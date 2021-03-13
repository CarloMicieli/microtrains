plugins {
    id("microtrains.library-conventions")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":catalog"))
    implementation(project(":collecting"))
}