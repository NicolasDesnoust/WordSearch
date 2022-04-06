all: build-app

.PHONY: build-app
build-app:
	@docker build . -t wordsearch

.PHONY: test
test:
	@docker build . --target test

.PHONY: sonar-scan
sonar-scan:
	@docker build . --target sonar-scan \
	--build-arg SONAR_ORGANIZATION=${SONAR_ORGANIZATION} \
	--build-arg SONAR_PROJECT_KEY=${SONAR_PROJECT_KEY} \
	--build-arg SONAR_HOST_URL=${SONAR_HOST_URL} \
	--build-arg SONAR_LOGIN=${SONAR_LOGIN} \
	--build-arg GITHUB_TOKEN=${GITHUB_TOKEN}

.PHONY: export-docs
export-docs:
	@docker build . --target export-docs --output docs/build/site
