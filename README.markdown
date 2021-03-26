# microtrains

[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![GitHub last commit](https://img.shields.io/github/last-commit/CarloMicieli/microtrains)
![Build](https://github.com/CarloMicieli/microtrains/workflows/build/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/CarloMicieli/microtrains/badge.svg?branch=main)](https://coveralls.io/github/CarloMicieli/microtrains?branch=main)

## Requirements

- [ ] Java JDK 11 ([https://adoptopenjdk.net/](https://adoptopenjdk.net/))
- [ ] Docker ([https://docs.docker.com/engine/install/](https://docs.docker.com/engine/install/))
- [ ] Docker compose

## How to run

```
$ ./gradlew

> Task :webapi:compileJava
Note: Generating OpenAPI Documentation
Note: Writing OpenAPI YAML to destination: /webapi/build/classes/java/main/META-INF/swagger/microtrains-v1.yml
Note: Writing OpenAPI View to destination: /webapi/build/classes/java/main/META-INF/swagger/views/swagger-ui/index.html

> Task :webapi:run
 __  __ _                                  _   
|  \/  (_) ___ _ __ ___  _ __   __ _ _   _| |_ 
| |\/| | |/ __| '__/ _ \| '_ \ / _` | | | | __|
| |  | | | (__| | | (_) | | | | (_| | |_| | |_ 
|_|  |_|_|\___|_|  \___/|_| |_|\__,_|\__,_|\__|
  Micronaut (v2.4.1)

11:40:26.562 [main] INFO  io.micronaut.runtime.Micronaut - Startup completed in 740ms. Server Running: http://localhost:8080
```

OpenApi definition for the web api is available at [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui).

## Modules

* `common` contains generic data type for the application (like `Length`s) and all interfaces for the **clean architecture** implementation (use cases and queries)
* `domain` contains domain entities
* `application` contains the business logic for application
* `infrastructure` contains the actual persistence implementation for use case and queries, it also contains the infrastructure code for security, and the web layer. This is the first module with a dependency on the spring framework
* `webapi` the rest-ful web api layer

## Clean architecture

### Use cases

**Use cases** are managing commands in the application. They are basically functions that given a __use case input__ are producing a __use case output__. In this implementation the output is produced as a side effect on a corresponding __output port__.

```java
public interface UseCaseInput {}

@FunctionalInterface
public interface UseCase<InType extends UseCaseInput> {
  void execute(InType input);
}
```

### Queries

On the other hand, **Queries** are handling the reading side of the application. The application is using different kind of queries

```java

public interface Query<C extends Criteria, T> {}

public interface SingleResultQuery<C extends Criteria, T> extends Query<C, T> {
  Optional<T> execute(C criteria);
}

public interface PaginatedQueryWithCriteria<C extends Criteria, T> extends Query<C, T> {
  PaginatedResult<T> execute(C criteria, Page currentPage, Sorting orderBy);
}
```

