// build.gradle.kts
plugins {
    alias(libs.plugins.kotlin) apply false
    kotlin("multiplatform") version libs.versions.kotlin.get()
}

repositories {
    mavenCentral()
    google()
}

kotlin {
    macosX64("macNative") { // on macOS
        binaries {
            executable()
        }
    }
    linuxX64("linuxNative") {     // on Linux
        binaries {
            executable()
        }
    }
    mingwX64("winNative") {// on Windows
        binaries {
            executable()
        }
    }

}