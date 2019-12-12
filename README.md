# Quarkus in Real World Deployments

This is the Github repository with sample applications for the presentation 
[Quarkus in Real World Deployments](https://docs.google.com/presentation/d/1w_H2VJsKRZYAwfSczICoKsmRfYrMPRUUgarl0QcVDgo/edit?ts=5d6eee0d#slide=id.p).

The purpose of these sample applications is to showcase typical use cases and the experience that the authors had when 
adopted [Quarkus](https://quarkus.io) to develop applications.  

## Pre Requisites

* [OpenJDK 11](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot)
* [GraalVM 19.1.1](https://github.com/oracle/graal/releases)
* [Maven 3.6.2](https://maven.apache.org/download.cgi)
* [Docker](https://hub.docker.com/search/?type=edition&offering=community)

## Project Contents

The project showcases two services. The Legume Service, used to Create, Read, Update and Delete Legumes. Each time a 
Legume is created, a new event with that information is produced and published to RabbitMQ. This allows other services, 
interested in the event to subscribe and consume it to perform additional actions.

The Hero Service consumes the event published by the Legume Service and transform the Legume into a Super Hero Legume.

Both Legume and Hero Service store the data into a Posgres database.

A separate R Service also exists in the project structure. This service showcases the Polyglot capabilities of GraalVM 
by mixing Java and R code in the same project. The R Service exposes a Calculator with the most common operations, with 
JAX-RS and the actual Calculator functions are provided by R.  

## Project Structure

The project is split into several modules:
* legume-service (a CRUD service to manage Legumes. When a new Legume is created it will be posted to RabbitMQ).
* hero-service (Consumes messages from RabbitMQ. It will transform Legumes into Hero Legumes).
* camel-support (A custom Camel ProcessorFactory to fix a cache issue with the RabbitMQ adapter).
* r-service (A Calculator service, with operations implemented in R and exposed with JAX-RS endpoints).

## Build
Just use Maven to build the project with the following command:

```bash
mvn install
```

### With Docker

You can also build Docker Images to run the apps:

```bash
docker-compose build
```

## Run

### With Docker

The easiest way to run the the entire system is to use `docker-compose`. This will run the apps, plus some 
required infrastructure, like a posgres database and a rabbitmq message broker. 

```bash
docker-compose up
```

And then you can use the following command to remove all the containers:

```bash
docker-compose down
```

### With Java

You always going to need to use `docker-compose` to run the required infrastructure, but you can run the apps directly 
with your JVM so you can play and change them around. Run only the infrastructure:

```bash
docker-compose up database rabbitmq
```

Then you run both `legume-service` and `hero-service` by going to each module folder and running the commands (depending on the project):

```bash
java -jar target/legume-service-1.0-SNAPSHOT-runner.jar

java -jar target/hero-service-1.0-SNAPSHOT-runner.jar
```

## Test the Applications

There is no UI to test the application. You can use a `curl` command to send a payload and check the applications:

```bash
curl -v -XPOST http://localhost:8080/legumes -H 'Content-Type: application/json' -d '{"name":"Broccoli","description":"Green Plant"}' 
```

## Native Images

Install GraalVM Native Image binary with:

```bash
gu install native-image
``` 

Make sure that you have an envirobment variable `GRAALVM_HOME` pointing to your GraalVM installation. With this you 
should be able to compile both Legume and Hero services as native binaries and run them without a JVM:

```bash
mvn package -Pnative
```

This is going to generate a binary executable. Run with:

```bash
./target/legume-service-1.0-SNAPSHOT-runner.jar

./target/hero-service-1.0-SNAPSHOT-runner.jar
```

## Polyglot Java - R Service

### Build 

Make sure that you use GraalVM as your JVM to run the application. You need to point `JAVA_HOME` to GraalVM.

First your must have the `R` language installed in your GraalVM:

```bash
gu install R
```

Then, just use Maven to build the project:

```bash
mvn install -Pall
```

### Run

Move to the `r-service folder` and run:

```bash
java -jar target/r-service-1.0-SNAPSHOT-runner.jar
```

### Test

Just use the following `curl` commands to perform operations:

```bash
curl -XPOST http://localhost:8090/calculator/subtract -d 'x=1&y=2.1'
```

```bash
curl -XPOST http://localhost:8090/calculator/add -d 'x=1&y=1'
```

The first call is slow, since it requires to start the R context. After that, subsequent calls are fast.
