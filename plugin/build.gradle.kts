plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "team.devblook"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")

    implementation(project(":pepito-core-api"))
}

tasks {
    build {
        dependsOn(reobfJar)
    }

    shadowJar {
        minimize()
        archiveFileName.set("pepito-core-${project.version}.jar")
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
        val props = mapOf(
            "version" to project.version,
            "apiVersion" to "1.20"
        )

        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}