plugins {
    id("microtrains.library-conventions")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":application"))
}