plugins {
    id("application")
    alias(libs.plugins.kotlin)
}

application {
    mainClass = "MainKt"
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.common.cli)
    implementation("org.jocl:jocl:2.0.4")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}