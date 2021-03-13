import org.gradle.api.tasks.testing.logging.TestExceptionFormat.*
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import io.github.carlomicieli.*

plugins {
    java
    jacoco
    id("com.diffplug.spotless")
    id("com.github.kt3k.coveralls")
}

repositories {
    mavenCentral()
}

configurations {
    implementation {
        resolutionStrategy.failOnVersionConflict()
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.compileJava {
    options.isIncremental = true
    options.isFork = true
    options.isFailOnError = false
}

version = "0.1"
group = "io.github.carlomicieli"

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            info.events(PASSED, FAILED, SKIPPED)
            exceptionFormat = FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
        }
        failFast = false
    }
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
        googleJavaFormat("1.9")
        endWithNewline()
        trimTrailingWhitespace()
        licenseHeaderFile("${project.rootDir}/license-header")
    }
}

jacoco {
    toolVersion = "0.8.6"
    reportsDirectory.set(file("${project.rootDir}/build/jacoco-report-dir"))
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.5".toBigDecimal()
            }
        }

        rule {
            enabled = false
            element = "CLASS"
            includes = listOf("org.gradle.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}

dependencies {
    implementation(platform("io.micronaut:micronaut-bom:${Micronaut.Version}"))
    annotationProcessor("org.projectlombok:lombok:${Lombok.Version}")
    testAnnotationProcessor("org.projectlombok:lombok:${Lombok.Version}")
    compileOnly("org.projectlombok:lombok:${Lombok.Version}")
    runtimeOnly("ch.qos.logback:logback-classic")
    testCompileOnly("org.projectlombok:lombok:${Lombok.Version}")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito:mockito-core:3.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:3.8.0")
}