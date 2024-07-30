plugins {
    kotlin("jvm") version "2.0.0"
}

object Deps {
    object JetBrains {
        object Kotlin {
            const val VERSION = "2.0.0"
        }
    }
}

group = "deus.away.engine"
version = "0.2.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("com.badlogicgames.gdx:gdx:1.12.1") // LibGDX core
    implementation ("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.12.1")
    implementation ("com.badlogicgames.gdx:gdx-platform:1.12.1:natives-desktop")
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