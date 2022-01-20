all: build

.PHONY: build
build-app:
	@docker build .

.PHONY: test
test:
	@docker build . --target test

.PHONY: export-docs
export-docs:
	@docker build . --target export-docs --output docs/build/site
