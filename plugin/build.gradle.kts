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
    maven("https://repo.unnamed.team/repository/unnamed-public/")
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")

    implementation(project(":pepito-core-api"))

    // https://mvnrepository.com/artifact/com.google.inject/guice
    implementation("com.google.inject:guice:7.0.0")

    implementation("me.fixeddev:commandflow-bukkit:0.6.0")

    // https://mvnrepository.com/artifact/net.jodah/expiringmap
    implementation("net.jodah:expiringmap:0.5.11")

    compileOnly("net.luckperms:api:5.4")
}

tasks {
    assemble {
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