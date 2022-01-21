all: build

.PHONY: build
build-app:
	@docker build .

.PHONY: test
test:
	@docker build . --target test

.PHONY: sonar-scan
sonar-scan:
	@docker build . --target sonar-scan \
	--build-arg SONAR_ORGANIZATION=${SONAR_ORGANIZATION} \
	--build-arg SONAR_PROJECT_KEY=${SONAR_PROJECT_KEY} \
	--build-arg SONAR_HOST_URL=${SONAR_HOST_URL} \
	--build-arg SONAR_LOGIN=${SONAR_LOGIN}

.PHONY: export-docs
export-docs:
	@docker build . --target export-docs --output docs/build/site
