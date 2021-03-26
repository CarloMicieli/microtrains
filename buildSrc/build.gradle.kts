plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("com.adarshr:gradle-test-logger-plugin:2.1.1")
    implementation("com.github.jengelman.gradle.plugins:shadow:6.1.0")
    implementation("io.micronaut.gradle:micronaut-gradle-plugin:1.4.2")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:5.11.0")
    implementation("com.bmuschko:gradle-docker-plugin:6.7.0")
    implementation("gradle.plugin.org.kt3k.gradle.plugin:coveralls-gradle-plugin:2.11.0")
}