plugins {
    `java-library`
    jacoco
    id("com.diffplug.spotless") version "6.25.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.sonarqube") version "5.1.0.4882"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.google.flatbuffers:flatbuffers-java:23.5.26")
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
    targetExclude("src/main/java/CoffeeTime/")
  }
}

version = "0.0.0-alpha.0.2.0"

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required = true
        csv.required = false
    }
}

sonar {
    properties {
        property("sonar.projectKey", "Argos")
        property("sonar.host.url", "http://sonarqube-argos.ukwest.cloudapp.azure.com/")
        property("sonar.token", "sqa_c089fd5649947c3bcfe1bafc2a710e9639271b91")
    }
}
