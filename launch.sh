#/bin/bash

mvn clean
rm -rf springapp/src/main/resources/static/built
mvn install

cd springapp
webpack

mvn spring-boot:run
