# aiCliPtyApi

Common AiCliPty API interface definition, provides an abstract pseudo-Terminal interface for interacting with various AI CLI implementations like ClaudeCode, Codex, GeminiCLI, etc.

The cannonical implementations are found in https://github.com/CodexCoder21Organization/aiCliPty/

## Overview

This module provides the platform-agnostic interface definitions for pseudo-terminal operations used throughout the AiCliGui project. It defines the common contract that platform-specific implementations must follow.

## Features

- **Multiplatform Support**: Kotlin Multiplatform module supporting JVM and WebAssembly targets
- **Common Interface**: Provides expect/actual declarations for PTY operations
- **Lightweight**: Interface-only module with no platform-specific implementation

## Module Structure

```
aiCliPtyApi/
├── src/
│   └── commonMain/
│       └── kotlin/tools/kotlin/aicligui/pty/
│           └── AiCliPty.kt    # Common PTY interface definitions
└── build.gradle.kts
```

## Usage

This module is typically included via Gradle composite builds in the parent AiCliGui project:

```kotlin
// In settings.gradle.kts
includeBuild("first_party/aiCliPtyApi")

// In build.gradle.kts
dependencies {
    api("tools.kotlin.aicligui:aiCliPtyApi:1.0.0")
}
```

## Building

```bash
./gradlew build
```

## Related Modules

- **aiCliPty**: JVM implementation of this API using pty4j and JediTerm
- **AiCliGui**: Main application that consumes this API

## License

Part of the AiCliGui project.

## Repository

This module is a git submodule of the main AiCliGui repository.

- Main Repository: https://github.com/CodexCoder21Organization/AiCliGui
- This Module: https://github.com/CodexCoder21Organization/aiCliPtyApi
