buildscript {
    repositories {
        mavenCentral()
    }
}

defaultTasks("clean", "webapi:run")