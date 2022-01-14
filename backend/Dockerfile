FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine as build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY tessdata tessdata

COPY src src

RUN ./mvnw verify
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

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
