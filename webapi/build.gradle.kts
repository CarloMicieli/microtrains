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
    annotationProcessor("io.micronaut.openapi:micronaut-openapi")

    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("io.micronaut.rxjava3:micronaut-rxjava3")

    implementation(project(":catalog"))
    implementation(project(":collecting"))
    implementation(project(":infrastructure"))
}

application {
    mainClass.set("io.github.carlomicieli.Application")
}