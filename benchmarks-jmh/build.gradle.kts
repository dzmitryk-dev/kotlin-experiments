plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.jmh.gradle)
}

group = "dzkoirn"
version = "unspecified"

repositories {
    mavenCentral()
    google()
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