plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = "5.8.2")
    compileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.24")
    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.24")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
