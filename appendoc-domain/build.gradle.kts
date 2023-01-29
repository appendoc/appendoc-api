/**
 * appendoc domain build kts
 */
plugins {
    id("java")
    id("appendoc.api.java-library-conventions")
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.auth0:java-jwt:4.2.2")
}
