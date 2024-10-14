plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.twelveoclock"
version = "1.0.0"

repositories {

    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/public/") {
        name = "SpigotMC"
    }
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "PaperMC"
    }
}

dependencies {

    compileOnly("org.spigotmc:spigot-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly("it.unimi.dsi:fastutil:8.5.14")

    implementation("org.jetbrains:annotations:26.0.1")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-core:2.18.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-toml:2.18.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.2")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.17:1.13.0")
}


tasks {

    test {
        useJUnitPlatform()
    }

    shadowJar {
        relocate("com.fasterxml", "dev.twelveoclock.printpackets.libs.com.fasterxml")
        relocate("org.jetbrains", "dev.twelveoclock.printpackets.libs.org.jetbrains")
        relocate("org.intellij", "dev.twelveoclock.printpackets.libs.org.intellij")
    }
}

