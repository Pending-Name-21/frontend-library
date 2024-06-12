object Meta {
    const val desc = "This library allows the game developer to write games."
    const val githubRepo = "Pending-Name-21/frontend-library"
    const val license = "MIT License"
    const val licenseUrl = "http://www.opensource.org/licenses/mit-license.php"
    const val developerId = "teran-joseluis"
    const val developerName = "José Luis Terán"
    const val developerOrganization = "Pending-Name-21"
    const val developerOrganizationUrl = "https://github.com/Pending-Name-21"
}

plugins {
    `java-library`
    id("com.diffplug.spotless") version "6.25.0"
    `maven-publish`
    signing
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
    withJavadocJar()
    withSourcesJar()

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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = findProperty("group") as String
            artifactId = findProperty("artifact") as String
            version = "0.0.0-alpha.0.2.0"

            from(components["java"])

            pom {
                name.set(rootProject.name)
                description.set(Meta.desc)
                url.set("https://github.com/${Meta.githubRepo}")

                licenses {
                    license {
                        name.set(Meta.license)
                        url.set(Meta.licenseUrl)
                    }
                }

                developers {
                    developer {
                        id.set(Meta.developerId)
                        name.set(Meta.developerName)
                        organization.set(Meta.developerOrganization)
                        organizationUrl.set(Meta.developerOrganizationUrl)
                    }
                }

                scm {
                    url.set("https://github.com/${Meta.githubRepo}.git")
                    connection.set("scm:git:git://github.com/${Meta.githubRepo}.git")
                    developerConnection.set("scm:git:git://github.com/${Meta.githubRepo}.git")
                }

                issueManagement {
                    url.set("https://github.com/${Meta.githubRepo}/issues")
                }
            }
        }
    }
}

signing {
    val signingKey = providers.environmentVariable("GPG_SIGNING_KEY")
    val signingPassphrase = providers.environmentVariable("GPG_SIGNING_PASSPHRASE")

    if (signingKey.isPresent && signingPassphrase.isPresent) {
        useInMemoryPgpKeys(signingKey.get(), signingPassphrase.get())
        val extension = extensions.getByName("publishing") as PublishingExtension
        sign(extension.publications)
    }
}
