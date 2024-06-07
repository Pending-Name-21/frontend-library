plugins {
    `java-library`
    id("com.diffplug.spotless") version "6.25.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

spotless {
  java {
    googleJavaFormat("1.22.0").aosp().reflowLongStrings().skipJavadocFormatting()
    // fix formatting of type annotations
    formatAnnotations()
  }
}

version = "0.0.0-alpha.0.2.0"

