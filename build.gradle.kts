plugins {
    kotlin("jvm") version "2.1.0"
    application
}

group = "me.i518388"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}



kotlin {
    jvmToolchain(21)
}