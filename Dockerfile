# syntax = docker/dockerfile:1.3

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
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw dependency:go-offline

COPY backend/src ./src

####################################################
### Build Target
####################################################
FROM base as build

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

####################################################
### Test Target
####################################################
FROM base as test

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw verify

####################################################
### Sonar-scan Target
####################################################
FROM test as sonar-scan

ARG SONAR_PROJECT_KEY
ARG SONAR_HOST_URL
ARG SONAR_LOGIN
ARG SONAR_ORGANIZATION

COPY .git /workspace/.git

RUN ./mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
    -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
    -Dsonar.host.url=${SONAR_HOST_URL} \
    -Dsonar.login=${SONAR_LOGIN} \
    -Dsonar.organization=${SONAR_ORGANIZATION}

####################################################
### Build-docs Target
####################################################
FROM test as build-docs
WORKDIR /workspace/docs

# Install Node & Npm
ENV NODE_VERSION=16.13.0
RUN apt install -y curl
RUN mkdir /usr/local/.nvm
ENV NVM_DIR=/usr/local/.nvm
RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash \
    && . $NVM_DIR/nvm.sh
RUN . "$NVM_DIR/nvm.sh" && nvm install ${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm use v${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm alias default v${NODE_VERSION}
ENV PATH="${NVM_DIR}/versions/node/v${NODE_VERSION}/bin/:${PATH}"
RUN node --version
RUN npm --version

RUN npm i -g @antora/cli@2.3 @antora/site-generator-default@2.3
RUN npm i -g redoc-cli@0.13.2

COPY docs .
COPY .git /workspace/.git

RUN antora antora-playbook.yml
RUN redoc-cli bundle -o build/site/api-rest.html /workspace/backend/target/openapi.json

####################################################
### Export-docs Target
####################################################
FROM scratch AS export-docs
COPY --from=build-docs /workspace/docs/build/site /

####################################################
### App Target (default)
####################################################
FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
VOLUME /tmp

RUN addgroup -S wordsearch && adduser -S wordsearch -G wordsearch

ENV TEMPORARY_DIRECTORY_PATH /wordsearch-tmp
RUN mkdir -m 700 ${TEMPORARY_DIRECTORY_PATH}
RUN chown wordsearch ${TEMPORARY_DIRECTORY_PATH}

ENV TESSDATA_PREFIX /workspace/backend/tessdata
COPY --from=build ${TESSDATA_PREFIX} ${TESSDATA_PREFIX}

ARG DEPENDENCY=/workspace/backend/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

USER wordsearch:wordsearch

CMD java -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8 -XX:+UseContainerSupport -cp app:app/lib/* io.github.nicolasdesnoust.wordsearch.WordSearchApplication
