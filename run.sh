#!/bin/bash
./gradlew clean build -x test
docker build -t local_appendoc .
