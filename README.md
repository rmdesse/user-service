
# Sample service using Spring Boot, Mongo DB and Redis (for caching)
[![Build Status](https://travis-ci.org/rmdesse/user-service.svg?branch=master)](https://travis-ci.org/rmdesse/user-service)


## Technologies

* [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven](https://maven.apache.org/)
* [Spring Boot](http://projects.spring.io/spring-boot/)
* [Swagger](http://swagger.io/)
* [MongoDB](https://www.mongodb.com/)
* [Redis](https://redis.io/)
* [Travis CI](https://travis-ci.org/)
* [Docker](http://docker.com/)


What is it?
------------

  This is a sample service which makes use of Spring Boot, Mongo DB and Redis (for caching) using
  containers (Docker)
  
  - Using embedded Mongo DB and Redis for Integration Testing
  - Using docker compose to stand a development environment with Mongo DB and Redis (for caching)
  
> **Note:** A docker image of application can also be built by running: `mvn package docker:build`
  
Installation
------------

  Run docker images (of Mongo DB and Redis) with docker compose at target/docker:
  
	`docker-compose up`
  
  Run application with command: `mvn spring-boot:run -Drun.profiles=dev`
  
  Services will be running under port 8080 (on localhost)
  
  > More information on services available at **v2/api-docs** or acessing page **swagger-ui.html#/user-controller**

Licensing
---------

  Please see the file called LICENSE.