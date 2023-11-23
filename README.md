# Wallet Crypto Component

## Introduction

The Wallet Crypto components is one of the services used by the Wallet Solution, and it is responsible to create, manage
and store the cryptographic material used by the Wallet.

## Key Components

### Key Generation Service
- Implement a robust private key generator.
- Integrate security measures to protect the generation process.

### Key Management Service
- Establish functions for Key management, rotation, and revocation.
- Implement security policies for DID administration.

### DID Generation Service
- Implement a DID generator.
- Integrate security measures to protect the generation process.

### DID Management Service
- Establish functions for DID management, rotation, and revocation.
- Implement security policies for DID administration.

### Cryptographic Storage Service
- Develop a secure system for private key storage.
- Implement encryption mechanisms to ensure confidentiality.

## Configurations

The componente is able to work with 2 different Vault solutions; HashiCorp Vault as default, and Azure Key Vault.

## Component Design

Wallet Crypto is designed using a set of practices and patterns:

### Creational patterns
- Builder pattern

### Structural patterns
- Facade Pattern

### Behavioral patterns


***
// todo

## Specifications

Wallet Crypto uses:
- Java 17
- Spring Boot 3.x
- Gradle 8.x
- Docker

### Features
- Reactive Web (WebFlux)
- OpenAPI 3.x documentation & Swagger UI
- Health check endpoint
- Logback logging
- Dockerized (multi-stage build)

### Dependencies
- Spring Boot Starter WebFlux
- Spring Boot Starter Actuator
- Spring Boot Starter Validation
- Spring Doc Open API WebFlux UI
- Lombok
- Logback
- Jackson
- Spring Boot Starter Test

## Getting Started

### Initial Project Configuration Changes
1. Clone this repository
2. Rename the project to your project name
3. Rename `rootProject.name` in `settings.gradle.kts` to your project name

### Docker Configuration Changes
1. Update `Dokerfile` to use the correct jar file name `ENTRYPOINT ["java", "-jar", "/app/demo-0.0.1SNAPSHOT.jar"]`.
2. Update `Dockerfile` Gradle tag to the latest version [here](https://hub.docker.com/_/gradle).
3. Update `Dockerfile` Java tag to the latest version [here](https://hub.docker.com/_/openjdk).

## Profiles

* **default**: This is the default profile that contains settings applicable to all environments. It serves as a baseline configuration for your application.

* **dev**: The dev profile is typically used for development environments. It may contain configurations specific to development, such as a local database setup, debugging options, and other settings that make development easier.

* **test**: The test profile is usually used for automated testing environments, like unit tests and integration tests. It can have configurations for in-memory databases or test-specific configurations that differ from the production setup.

* **prod**: The prod profile is meant for production environments. It contains configurations optimized for performance, security, and reliability. This is where your application runs in a real-world scenario.

## JaCoCo

Adding exclusions through `build.gradle`:

```
tasks.jacocoTestReport {
	dependsOn(tasks.test)
	...
	afterEvaluate {
		classDirectories.setFrom(files(classDirectories.files.collect {
			fileTree(dir: it, exclude: [
				'**/SkeletonApplication.class',
			])
		}))
	}
}
```

## Documentation
* **Api Docs**: `http://localhost:8081/api-docs`
* **Swagger-ui**: `http://localhost:8081/swagger-ui`

## Observability
* **Health**: `http://localhost:8081/health`
* **Metrics**: `http://localhost:8081/metrics`
* **Logging**: `http://localhost:8081/loggers`

## Checkstyle
Checkstyle configuration is located in `config/checkstyle/checkstyle.xml`. To run checkstyle, execute the following command:
```
./gradlew check
```

## OWASP Dependency Check
OWASP Dependency Check is a gradle plugin that checks project dependencies for known, publicly disclosed, vulnerabilities. To run OWASP Dependency Check, execute the following command:
```
./gradlew dependencyCheckAnalyze
```

## Technical Documentation

The Technical Documentation is located in the `docs` folder. This documentation is intended to be used as a reference for the project. It includes use cases, sequence diagrams, and other technical documentation.

To work with IntelliJ and documentation you will need to be installed some plugins:
- [AsciiDoc](https://plugins.jetbrains.com/plugin/7391-asciidoc)
- [PlantUML integration](https://plugins.jetbrains.com/plugin/7017-plantuml-integration)

## Best Practices

### Design
#### Basic-Required
- [ ] Backend for Frontend (BFF) as an API Gateway
- [ ] Database schema per service
- [x] Expose the /health endpoint
#### Medium
- [ ] Injection of external configuration at runtime -- Mediante IaC Repository
- [ ] Service Contracts Testing Agreement
#### Advanced
- [ ] Liquibase for database change management
- [ ] Dapr for event-driven programming
- [ ] Implement Distributed Tracing
- [ ] If High Performance is Required, Spring Native
- [x] Early and Frequent DevSecOps

### Programming
#### Basic-Required
- [ ] Development tools
  - [x] IntelliJ IDEA, Spring Tools for Eclipse or VSCode
  - [ ] SonarLint
  - [x] Checkstyle
  - [x] Jacoco
- [ ] Project structure
- [ ] Logging rules
- [x] Startup Profiles Configuration
- [x] Code documentation with Open API
- [x] Use of UI Swagger
- [ ] Input validation in controllers
- [x] Use of Lombok
- [x] Use of Checkstyle
#### Medium
- [ ] Project dependency standards
- [ ] Simple and clean controller layer
- [ ] Service layer focused on business logic
- [ ] Constructor injection instead of @Autowired
- [ ] Pagination and sorting with Spring Data JPA
- [ ] Unit testing with JUnit and Mockito
#### Advanced
- [x] JSON log configuration with Logback
- [ ] Global exception handling
- [ ] Avoid unnecessary additional dependencies
- [ ] Review Dependency Updates
- [ ] Use of Maven Wrapper