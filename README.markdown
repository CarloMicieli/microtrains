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
  Micronaut (v2.4.0)

11:40:26.562 [main] INFO  io.micronaut.runtime.Micronaut - Startup completed in 740ms. Server Running: http://localhost:8080
```

OpenApi definition for the web api is available at [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui).

## Modules

* `common` contains generic data type for the application (like `Length`s) and all interfaces for the **clean architecture** implementation (use cases and queries)
* `catalog` domain entities and business logic for the **catalog** module
* `collecting` domain entities and business logic for the **collecting** module
* `infrastructure` contains the actual persistence implementation for use case and queries, it also contains the infrastructure code for security, and the web layer. This is the first module with a dependency on the spring framework
* `webapi` the rest-ful web api layer
