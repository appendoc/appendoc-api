/**
 * appendoc webserver build kts
 */
plugins {
    id("java")
    id("appendoc.api.java-application-conventions")
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

dependencies {
    implementation(project(":appendoc-domain"))
    implementation(project(":appendoc-application"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
}

application {
    mainClass.set("wiki.appendoc.AppendocWikiApplication")
}