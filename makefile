
build:
	mvn clean install

dependencies:
	mvn dependency:copy-dependencies -DoutputDirectory=target/dependency -DincludeScope=runtime -DexcludeTransitive=false
