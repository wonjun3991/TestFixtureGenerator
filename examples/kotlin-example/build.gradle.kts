plugins {
    application
    kotlin("jvm") version "2.1.20"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.wonjun3991:TestFixtureGenerator:v1.2.0")
}

application {
    mainClass.set("org.example.MainKt")
}
