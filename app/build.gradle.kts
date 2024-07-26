version = "1.0.0"

plugins {
    kotlin("jvm") version "1.9.23"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":engine")) // Incluye el proyecto `core` si es un módulo separado
}

application {
    mainClass.set("app.MainKt")
}

tasks.shadowJar {
    archiveFileName.set("Away ${project.version}.jar")
    archiveClassifier.set("")
    manifest {
        attributes["Main-Class"] = "app.MainKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.startScripts {
    dependsOn(tasks.shadowJar)
    // Configura aquí otros ajustes si es necesario
}

tasks.distZip {
    dependsOn(tasks.shadowJar)
    // Configura aquí otros ajustes si es necesario
}

tasks.distTar {
    dependsOn(tasks.shadowJar)
    // Configura aquí otros ajustes si es necesario
}
