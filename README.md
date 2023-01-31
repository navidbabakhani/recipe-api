# Getting Started - Recipe API

This is a simple RESTful Java microservice for handling users' recipes. 

Technology used in this microservice:

* Java 11
* Spring Boot 2.7.8
* Spring Data JPA
* Liquibase Migration
* PostgreSQL - for production env
* H2 - for integration test
* Spring Boot Actuator - for production environment management and health monitoring
* OpenApi (Swagger documentation)
* Lombok
* Spring Validation
* JUnit 5 and Mockito for unit and integration tests

##  Building the application
* Use jdk 11 to run/build this application
* Build the application by running mvn clean install

## Running the application
Start some docker container (for PostgreSQL) by:
```
docker compose up -d
```

All related database configs are aligned with this docker container.

After running database and making sure an instance is running on `localhost:5432`, then you can use this command to run the application:
```
./mvnw spring-boot:run
```

For checking application is up and running, you can check this url: `http://localhost:8080/actuator/health` and see: `{"status":"UP"}`

## How to test Recipes Api

There are a couple of endpoints in this service to call:
(the Swagger api documentation can be found in: `project_root/src/main/resources/api-specs/recipe-api.yml`)

NOTE: all recipes endpoints require `userId` in request header. You can use this value:
* 9f9349a6-19ce-46fe-a0cf-b4bffaba8772

Or create a user (POST /api/v1/users) and use its userId. 