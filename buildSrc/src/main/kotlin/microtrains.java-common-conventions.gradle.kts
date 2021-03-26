import org.gradle.api.tasks.testing.logging.TestExceptionFormat.*
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import io.github.carlomicieli.*

plugins {
    java
    jacoco
    id("com.adarshr.test-logger")
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
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
        vendor.set(JvmVendorSpec.ADOPTOPENJDK)
        implementation.set(JvmImplementation.VENDOR_SPECIFIC)
    }
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
            events(PASSED, FAILED, STANDARD_ERROR, SKIPPED)
            exceptionFormat = FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
            showStandardStreams = true
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

coveralls {
    jacocoReportPath = "${project.rootDir}/code-coverage-report/build/reports/jacoco/codeCoverageReport/codeCoverageReport.xml"
}

// Do not generate reports for individual projects
tasks.jacocoTestReport {
    enabled = false
}

// Share sources folder with other projects for aggregated JaCoCo reports
configurations.create("transitiveSourcesElements") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("source-folders"))
    }
    sourceSets.main.get().java.srcDirs.forEach {
        outgoing.artifact(it)
    }
}

// Share the coverage data to be aggregated for the whole product
configurations.create("coverageDataElements") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("jacoco-coverage-data"))
    }
    // This will cause the test task to run if the coverage data is requested by the aggregation task
    outgoing.artifact(tasks.test.map { task ->
        task.extensions.getByType<JacocoTaskExtension>().destinationFile!!
    })
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