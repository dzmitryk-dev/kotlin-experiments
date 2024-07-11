plugins {
    kotlin("jvm")
    alias(libs.plugins.jmh.gradle)
}

group = "dzkoirn"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    jmh(libs.jmh)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}