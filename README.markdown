# microtrains

[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![GitHub last commit](https://img.shields.io/github/last-commit/CarloMicieli/microtrains)
![Build](https://github.com/CarloMicieli/microtrains/workflows/build/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/CarloMicieli/microtrains/badge.svg?branch=main)](https://coveralls.io/github/CarloMicieli/microtrains?branch=main)

## How to run

```
docker run --rm --name postgres-dev \
  -e POSTGRES_PASSWORD=mysecretpassword \
  -e POSTGRES_DB=trainsdb \
  -d -p 5432:5432 -v postgres_data:/var/lib/postgresql/data postgres
```

```
$ ./gradlew

> Task :webapi:run
 __  __ _                                  _   
|  \/  (_) ___ _ __ ___  _ __   __ _ _   _| |_ 
| |\/| | |/ __| '__/ _ \| '_ \ / _` | | | | __|
| |  | | | (__| | | (_) | | | | (_| | |_| | |_ 
|_|  |_|_|\___|_|  \___/|_| |_|\__,_|\__,_|\__|
  Micronaut (v2.4.0)

<============-> 96% EXECUTING [31s]
> :webapi:run
```
