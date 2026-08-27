import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.dokka)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.vanniktech.maven.publish)
    alias(libs.plugins.jetbrains.kotlinx.binary.compatibility.validator)
}

kotlin {
    android {
        namespace = "com.paligot.jsonforms.kotlin"
        compileSdk = 36
        minSdk = 26
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.jetbrains.kotlinx.collections)
            api(libs.jetbrains.kotlinx.serialization.json)
            implementation(libs.jetbrains.kotlinx.coroutines)
        }
        commonTest.dependencies {
            implementation(libs.jetbrains.kotlin.test)
        }
    }
}

tasks {
    withType<KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    withType<JavaCompile>().configureEach {
        val javaToolchains = project.extensions.getByType<JavaToolchainService>()
        javaCompiler.set(
            javaToolchains.compilerFor {
                languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_21.toString()))
            },
        )
    }
}

mavenPublishing {
    pom {
        name.set("core")
        description.set("Core data models for JSON Schema, UI Schema, and the data handled by the forms")
    }
}
