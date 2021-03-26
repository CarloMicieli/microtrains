plugins {
    id("microtrains.library-conventions")
}

dependencies {
    implementation("commons-validator:commons-validator:1.7")
    implementation("com.googlecode.libphonenumber:libphonenumber:8.12.20")
    implementation("io.micronaut:micronaut-validation")

    testImplementation("org.glassfish:javax.el:3.0.0")
    testImplementation("org.hibernate:hibernate-validator:6.0.13.Final")
}