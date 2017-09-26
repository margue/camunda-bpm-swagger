#!/bin/sh

# Download
# wget http://central.maven.org/maven2/io/swagger/swagger-codegen-cli/2.2.3/swagger-codegen-cli-2.2.3.jar -O swagger-codegen-cli.jar

mkdir -p target/python
java -jar swagger-codegen-cli.jar generate -i target/swagger.json -l python -o target/python
