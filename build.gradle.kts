plugins {
    alias(libs.plugins.kotlinMultiplatform)
    `maven-publish`
}

group = "tools.kotlin.aicligui"
version = "1.0.0"

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

kotlin {
    // Provide a JVM target so the module builds in this repo; code lives in commonMain
    jvm() {
        // Ensure consistent classfile target for downstream JVM consumers
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    jvmToolchain(17)

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.library()
    }

    sourceSets {
        commonMain.dependencies {}
        jvmMain.dependencies {}
    }
}
