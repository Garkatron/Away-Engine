plugins {
    kotlin("jvm") version "1.9.23"
}

object Deps {
    object JetBrains {
        object Kotlin {
            const val VERSION = "2.0.0"
        }
    }
}

group = "deus.away.engine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies")
    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies-maven")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlin:kotlin-scripting-common")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host")
    runtimeOnly("org.jetbrains.kotlin:kotlin-scripting-jsr223:${Deps.JetBrains.Kotlin.VERSION}")
    runtimeOnly("org.jetbrains.kotlin:kotlin-scripting-common:${Deps.JetBrains.Kotlin.VERSION}")
    runtimeOnly("org.jetbrains.kotlin:kotlin-scripting-jvm:${Deps.JetBrains.Kotlin.VERSION}")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-toml:2.17.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}