import io.github.carlomicieli.Micronaut;

plugins {
    id("microtrains.library-conventions")
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-inject-java:${Micronaut.Version}")

    implementation(project(":common"))
    implementation(project(":domain"))

    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-inject-java:${Micronaut.Version}")

    testImplementation("org.glassfish:javax.el:3.0.0")
    testImplementation("org.hibernate:hibernate-validator:6.0.13.Final")
}