all: build

.PHONY: build
build-app:
	@docker build .

.PHONY: test
test:
	@docker build . --target test
