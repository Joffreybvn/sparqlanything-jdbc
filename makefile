.PHONY: build bundle test

build:
	mvn clean package
	# Copy jar into /build folder
	mkdir -p ./build
	JAR_FILE=$$(ls target/sparql-anything-jdbc-*.jar); \
	cp "$${JAR_FILE}" "./build/"

bundle: build
	mvn dependency:copy-dependencies \
    		-DoutputDirectory=build/bundle \
    		-DincludeScope=runtime \
    		-DexcludeTransitive=false

	# Copy the main jar into build/bundle
	JAR_FILE=$$(ls target/sparql-anything-jdbc-*.jar); \
	cp "$${JAR_FILE}" "./build/bundle/"

	# Remove all files in 'build/bundle' that are not *.jar
	find ./build/bundle -type f ! -name '*.jar' -delete

	# Zip the bundle folder
	cd ./build && tar -czvf bundle.tar.gz bundle

test: bundle
	mvn test
	python -m pytest ./src/test/python/test_jaydebeapi.py
