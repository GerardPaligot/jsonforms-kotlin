import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.dokka)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.vanniktech.maven.publish)
    alias(libs.plugins.jetbrains.kotlinx.binary.compatibility.validator)
}

kotlin {
    android {
        namespace = "com.paligot.jsonforms.ui"
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
            baseName = "ui"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.shared)
            api(libs.jetbrains.kotlinx.serialization.json)
            api(compose.ui)
            api(compose.runtime)
            api(compose.foundation)
        }
        val desktopTest by getting {
            dependencies {
                implementation(compose.desktop.uiTestJUnit4)
                implementation(compose.desktop.currentOs)
                implementation(libs.jetbrains.kotlin.test)
                implementation(libs.io.mockk)
            }
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
        name.set("ui")
        description.set("Common UI components and layout system for JsonForms")
    }
}
