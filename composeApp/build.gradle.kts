import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
}

compose.desktop {
    application {
        mainClass = "com.paligot.jsonforms.kotlin.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.paligot.jsonforms.kotlin.desktop"
            packageVersion = "1.0.0"
        }
    }
}

kotlin {
    android {
        namespace = "com.paligot.jsonforms.kotlin.demo"
        compileSdk = 37
        minSdk = 26
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.renderers.material3)
            implementation(projects.renderers.cupertino)
            implementation(projects.shared)
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.material.icons.extended)
            implementation(libs.jetbrains.compose.ui)
            implementation(libs.jetbrains.compose.components.resources)
            implementation(libs.jetbrains.compose.components.ui.tooling.preview)
            implementation(libs.cupertino)
            implementation(libs.jetbrains.androidx.viewmodel.compose)
            implementation(libs.jetbrains.androidx.navigation.compose)
            implementation(libs.jetbrains.kotlinx.serialization.json)
            implementation(libs.bundles.io.ktor.client)
        }

        named("desktopMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.jetbrains.kotlinx.coroutines)
                implementation(libs.jetbrains.kotlinx.coroutines.swing)
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
