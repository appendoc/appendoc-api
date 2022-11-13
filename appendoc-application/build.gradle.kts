/**
 * appendoc application build kts
 */
plugins {
    id("java")
    id("appendoc.api.java-library-conventions")
}

dependencies {
    implementation(project(":appendoc-domain"))
}