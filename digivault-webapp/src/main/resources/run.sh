#!/bin/bash
# To run -  sh digivault-webapp/src/main/resources/run.sh
echo "Starting Build : "
mvn clean install -DskipTests
if [ $? -eq 0 ]
then
  echo "Build Succeeded. Starting server."
  cd digivault-webapp/
  java -jar target/digivault-webapp-1.0-SNAPSHOT.jar  server src/main/resources/config.yml
  # run in linux box. Kill using kill <pid>
  #nohup java -jar target/digivault-webapp-1.0-SNAPSHOT.jar  server src/main/resources/config.yml  &
else
  echo "Build Failed."
  exit -1
fi