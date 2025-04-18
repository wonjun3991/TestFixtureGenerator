plugins {
    application
    kotlin("jvm") version "2.1.20"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(project(":lib"))
}

application {
    mainClass.set("org.example.MainKt")
}
