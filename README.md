# myRetail Restful Service

## What's inside 
This project is based on the [Spring Boot](http://projects.spring.io/spring-boot/) project and uses the below tech stack :
- Java 8
- Maven 3.8.1
- Spring Boot
- Spring Data (Mongo DB)
- Swagger 2.9.2
- Spring Boot Actuator
- JUnit
- Mockito

# Project description

This is a simple Demo of end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller
using Java, Spring Boot, RESTful Service and Mongo DB. 

## Installation 
To build, or run project Java and MongoDB should be installed.

## How to build project

The project is created with Maven, so you just need to import it to your IDE and build the project to resolve the dependencies.
You can use the below command if you want to build the project without sing any IDE.
mvn clean install


## How to run project locally

Open the project in Eclipse or STS IDE  
Open TargetMyretailServiceApplication file and Right click to run it as SpringBoot App.

Running the project locally assumes that `java` and `MongoDB` are installed locally.

## How to work with the application

After application is started open the following into any browser [http://localhost:9000/api/swagger-ui.html](http://localhost:9000/api/swagger-ui.html)

Application also provides `REST` API's for  fetching product details for the given product id and for updating the product details.


