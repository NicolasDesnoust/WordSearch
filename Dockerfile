####################################################
### Base Target
####################################################
FROM adoptopenjdk/openjdk11:jdk-11.0.11_9 as base
WORKDIR /workspace/backend
ENV TESSDATA_PREFIX /workspace/backend/tessdata

COPY backend/mvnw ./mvnw
COPY backend/.mvn ./.mvn
COPY backend/pom.xml ./pom.xml
COPY backend/tessdata ./tessdata

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY backend/src ./src

####################################################
### Build Target
####################################################
FROM base as build

RUN ./mvnw package -DskipTests

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

####################################################
### Test Target
####################################################
FROM base as test

RUN ./mvnw verify

####################################################
### App Target (default)
####################################################
FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
VOLUME /tmp
RUN addgroup -S wordsearch && adduser -S wordsearch -G wordsearch
USER wordsearch:wordsearch
ENV TESSDATA_PREFIX /workspace/app/tessdata
COPY --from=build ${TESSDATA_PREFIX} ${TESSDATA_PREFIX}
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

CMD java -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8 -XX:+UseContainerSupport -cp app:app/lib/* io.github.nicolasdesnoust.wordsearch.WordSearchApplication
