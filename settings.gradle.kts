enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "jsonforms-kotlin"
include(":composeApp")
include(":ui")
include(":renderers:cupertino")
include(":renderers:material3")
include(":shared")