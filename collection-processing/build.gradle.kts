plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlinx.benchmark)
}

group = "collection.processing"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(libs.kotlinx.benchmark.runtime)
}

tasks.test {
    useJUnitPlatform()
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
