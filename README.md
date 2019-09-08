# quarkus-in-prod
Quarkus in production demo application.

## Requirements

- JDK 11+
- Docker 18.06.3-ce+
- Maven 3.6+

## Use it

#### Build
```shell script
mvn clean install
```
Build native image
```shell script
mvn clean package -Pnative
```
Build docker image with native code
```shell script
docker build -f src/main/docker/Dockerfile.native -t tdx/quarkus-in-prod .
```

#### Database startup command:
```shell script
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name test_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=test -p 5432:5432 postgres:latest
```

####Run docker image
```shell script
docker run --network=host -i --rm -p 8080:8080 tdx/quarkus-in-prod
```

#### Useful demo commands:

Add Fruit:
```shell script
curl -v -XPOST http://localhost:8080/fruits -H 'Content-Type: application/json' -d '{"description":"Tropical fruit","name":"banana"}'
```
List Fruits:
```shell script
curl -v http://localhost:8080/fruits
```
Fail add fruit:
```shell script
 curl -v -XPOST http://localhost:8080/fruits -H 'Content-Type: application/json' -d '{"description":"Tropical fruit","name":""}'
```

## Used Technologies

### Runtime
- CDI
- Post and Get endpoints with Jax-RS
- MP OpenApi
- MP Config
- MP HealthCheck
- Bean Validation
- JPA
- PostgreSQL
- Docker

### Tests
- Quarkus tests
- JUnit 5
- Restasured
- H2