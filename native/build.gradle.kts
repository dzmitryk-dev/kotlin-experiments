// build.gradle.kts
plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
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