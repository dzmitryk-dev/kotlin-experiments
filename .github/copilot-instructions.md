# Kotlin Experiments - AI Coding Instructions

## Project Overview
This is a multi-module Kotlin experimentation repository featuring diverse platform targets and technologies. The codebase serves as a testing ground for various Kotlin capabilities including multiplatform development, WebAssembly (WASM), native compilation, Android development, coroutines, and performance benchmarking.

## Architecture & Module Structure

### Composite Build Pattern
- **Root Level**: Uses Gradle composite builds with `includeBuild` declarations in `settings.gradle`
- **Each Module**: Self-contained with own `build.gradle.kts` and `settings.gradle.kts`
- **Independent Execution**: Modules can be built/run independently from their directories
- **Shared Dependencies**: Common versions managed via `gradle/libs.versions.toml` (Kotlin 2.2.0)

### Key Module Categories

**Performance & Benchmarking:**
- `benchmarks-jmh/` - JMH-based microbenchmarks using `me.champeau.jmh` plugin
- `benchmarks-kotlinx/` - kotlinx.benchmark framework with 5-second iteration times
- `computation/` - CPU-intensive algorithms (Mandelbrot sets, parallel streams) with image output to `output/` dir

**Platform Targets:**
- `native/` - Kotlin/Native multiplatform (macOS, Linux, Windows targets)
- `wasi-example/` - Kotlin/Wasm WASI with custom Deno integration and Node.js support
- `SpaceX-Demo/` - Android clean architecture (app/data/domain layers) with Koin DI
- `kmm-clock-experiment/` - Kotlin Multiplatform Mobile with Compose Multiplatform

**Concurrency:**
- `coroutines/` - Kotlin coroutines patterns with timing utilities and parallel execution examples

## Critical Development Workflows

### Building & Running
```bash
# Root level - builds all modules
./gradlew build

# Individual modules (from module directory)
cd benchmarks-jmh && ./gradlew jmh
cd wasi-example && ./gradlew wasmWasiNodeRun
cd native && ./gradlew macNativeRun  # or linuxNativeRun/winNativeRun
cd kmm-clock-experiment && ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

### WASI/WebAssembly Specifics
- Uses Kotlin 2.2.0 with experimental WASM DSL (`@OptIn(ExperimentalWasmDsl::class)`)
- Custom Deno integration with automatic executable generation
- Both Node.js and Deno runtime support with platform-specific executables
- Exception handling configurable via `-Xwasm-use-traps-instead-of-exceptions`

### Android Project Pattern
- Clean Architecture: `app` (UI) → `domain` (business logic) → `data` (repositories)
- Uses Koin for dependency injection, Glide for image loading
- Modern Android toolchain: compileSdk 32, Java 11, core library desugaring

## Project-Specific Conventions

### Build Configuration
- **Toolchain**: Java 21 for JVM modules (`kotlin { jvmToolchain(21) }`)
- **Testing**: JUnit Platform (`useJUnitPlatform()`) across all modules
- **Group Naming**: Descriptive groups like `"dzkoirn"`, `"collection.processing"`

### Code Patterns
- **Timing Utilities**: Suspend functions with `timeOf()` wrapper for performance measurement
- **Image Generation**: Computational results saved to `output/` directory as PNG files
- **Coroutine Patterns**: Explicit `Dispatchers.Default` usage for CPU-intensive parallel work

### Module Independence
- Each module has its own version catalog and plugin management
- No cross-module dependencies (except Android modules)
- Self-contained build scripts with minimal root-level coordination

## Integration & Dependencies

### External APIs
- **SpaceX API**: Real REST API integration in Android demo (`https://github.com/r-spacex/SpaceX-API`)
- **Performance Libraries**: JMH, kotlinx.benchmark for different benchmarking approaches

### Platform-Specific Considerations
- **Native Targets**: Separate binaries for each OS/architecture combination
- **WASM Targets**: Both WASI and JS environments supported
- **Android**: Standard Android Gradle Plugin with ProGuard disabled

## Key Files to Reference
- `gradle/libs.versions.toml` - Central dependency management
- `settings.gradle` - Composite build structure
- `wasi-example/build.gradle.kts` - Complex WASM/Deno integration example
- `computation/src/main/kotlin/Main.kt` - Image generation and performance measurement patterns
- `coroutines/src/main/kotlin/Main.kt` - Suspend function and parallel execution examples

When working with this codebase, always consider the target platform and use the appropriate module's build configuration. Each experiment is designed to be self-contained and independently executable.