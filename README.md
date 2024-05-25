# Introduction

This is the code implementation of the library, which will serve as a contract to enable the Game and Console talk with each other, so that the game can be executed.
Please access the [architecture repository](https://github.com/Pending-Name-21/arquitecture) to find documentation

# Getting Started
You can either download the latest release, or locally produce a JAR locally with  `$ ./gradlew jar`

# Usage
## Gradle

bridge library hasn't been published to MavenCentral yet. So it needs to be loaded locally.

```
repositories {
    mavenCentral()

    flatDir {
        dirs("path/to/bridge.jar")
    }
}
```

Note that the folder, where bridge.jar is, should be at the same level as the Gradle build file.

Then you can add bridge as any other dependency.

`    implementation("bridge:bridge-0.0.0")`

