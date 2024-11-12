# Feature Tracker Web Application

The ft-webapp is the feature tracker web application that talks to various backend services 
via api-gateway. 

## TechStack
* Java, Spring Boot
* Thymeleaf, Bootstrap CSS
* Spring Security OAuth 2, Keycloak
* Maven

## Prerequisites
* JDK 21 or later
* Docker ([installation instructions](https://docs.docker.com/engine/install/))
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* Keycloak

Refer [docker-compose based infra setup](https://github.com/feature-tracker/docker-infra) for running dependent services.

## How to get started?

```shell
$ git clone https://github.com/feature-tracker/ft-webapp.git
$ cd ft-webapp

# Run tests
$ ./mvnw verify

# Format code
$ ./mvnw spotless:apply

# Run application
# Once the dependent services (Keycloak, api-gateway, feature-service, etc) are started, 
# you can run/debug FeatureTrackerWebApplication.java from your IDE.
```
