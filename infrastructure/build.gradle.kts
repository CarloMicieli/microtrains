import io.github.carlomicieli.Micronaut;

plugins {
    id("microtrains.library-conventions")
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-inject-java:${Micronaut.Version}")

    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":application"))
    implementation("io.micronaut:micronaut-inject-java:${Micronaut.Version}")
}
