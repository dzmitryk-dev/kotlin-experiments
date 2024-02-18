// build.gradle.kts
plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

kotlin {
    macosX64("native") { // on macOS
        binaries {
            executable()
        }
    }
    linuxX64("native") {     // on Linux
        binaries {
            executable()
        }
    }
    mingwX64("native") {// on Windows
        binaries {
            executable()
        }
    }

}