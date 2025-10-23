plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinx.benchmark)
}

group = "collection.processing"
version = "unspecified"

repositories {
    mavenCentral()
    google()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(libs.kotlinx.benchmark.runtime)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

benchmark {
    configurations {
        named("main") {
            iterationTime = 5
            iterationTimeUnit = "sec"
        }
    }
    targets {
        register("main")
    }
}
