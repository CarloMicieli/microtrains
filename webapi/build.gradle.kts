plugins {
    id("microtrains.java-conventions")
}

micronaut {
    version(io.github.carlomicieli.Micronaut.Version)
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.github.carlomicieli.*")
    }
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-validation")
    annotationProcessor("io.micronaut.openapi:micronaut-openapi")
    annotationProcessor("io.micronaut.security:micronaut-security-annotations")

    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("io.micronaut.rxjava3:micronaut-rxjava3")

    implementation(project(":domain"))
    implementation(project(":application"))
    implementation(project(":infrastructure"))
}

application {
    mainClass.set("io.github.carlomicieli.Application")
}

tasks.compileJava {
    options.isIncremental = true
    options.isFork = true
    options.isFailOnError = false
    options.forkOptions.jvmArgs = listOf(
        "-Dmicronaut.openapi.views.spec=swagger-ui.enabled=true,swagger-ui.theme=flattop",
        "-Dmicronaut.openapi.property.naming.strategy=SNAKE_CASE")
}
