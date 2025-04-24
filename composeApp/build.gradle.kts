import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
}

android {
    namespace = "com.paligot.jsonforms.kotlin.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.paligot.jsonforms.kotlin.android"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
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
    androidTarget()
    jvm("desktop")

    sourceSets {
        val desktopMain by getting
        commonMain.dependencies {
            implementation(projects.material3)
            implementation(projects.cupertino)
            implementation(projects.shared)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.preview)
            implementation(libs.cupertino)
            implementation(libs.jetbrains.androidx.viewmodel.compose)
            implementation(libs.jetbrains.androidx.navigation.compose)
            implementation(libs.jetbrains.kotlinx.serialization.json)
            implementation(libs.bundles.io.ktor.client)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.jetbrains.kotlinx.coroutines)
            implementation(libs.jetbrains.kotlinx.coroutines.swing)
        }
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf("-opt-in=kotlin.RequiresOptIn")
            jvmTarget = JavaVersion.toVersion(JavaVersion.VERSION_21).toString()
        }
    }

    withType<JavaCompile> {
        val javaToolchains = project.extensions.getByType<JavaToolchainService>()
        javaCompiler.set(
            javaToolchains.compilerFor {
                languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_21.toString()))
            },
        )
    }
}
