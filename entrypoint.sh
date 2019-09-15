#!/bin/sh

set -e

echo Starting the application

echo Connecting to database server $DB_HOST:$DB_PORT and database $DB_DATABASE

java \
    -Dquarkus.http.host=0.0.0.0 \
    -Dquarkus.datasource.url=jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_DATABASE?currentSchema=public \
    -Dquarkus.datasource.username=$DB_USER \
    -Dquarkus.datasource.password=$DB_PASSWORD \
    -Dquarkus.hibernate-orm.log.sql=true \
    -jar /app/app.jar
