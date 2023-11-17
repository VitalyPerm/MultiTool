pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            val orbitVersion = "6.1.0"
            library("orbit-viewmodel", "org.orbit-mvi:orbit-viewmodel:$orbitVersion")
            library("orbit-compose", "org.orbit-mvi:orbit-compose:$orbitVersion")
            bundle("orbit", listOf("orbit-viewmodel", "orbit-compose"))

            val coilVersion = "2.5.0"
            library("coil", "io.coil-kt:coil-compose:$coilVersion")

            val gsonVersion = "2.10.1"
            library("gson", "com.google.code.gson:gson:$gsonVersion")

            val retrofitVersion = "2.9.0"
            library("retrofit-core", "com.squareup.retrofit2:retrofit:$retrofitVersion")
            library("retrofit-gson", "com.squareup.retrofit2:converter-gson:$retrofitVersion")
            bundle("retrofit", listOf("retrofit-core", "retrofit-gson"))

            val hiltVersion = "2.48.1"
            val hiltNavigationVersion = "1.1.0"
            library("hilt-android", "com.google.dagger:hilt-android:$hiltVersion")
            library("hilt-navigation", "androidx.hilt:hilt-navigation-compose:$hiltNavigationVersion")
            library("hilt-kapt", "com.google.dagger:hilt-compiler:$hiltVersion")
            bundle("hilt", listOf("hilt-android", "hilt-navigation"))

            val navigationComponentVersion = "2.7.5"
            library(
                "navigation",
                "androidx.navigation:navigation-compose:$navigationComponentVersion"
            )

            val composeVersion = "1.5.4"
            val material3Version = "1.1.2"
            val activityComposeVersion = "1.8.0"
            val coreKtxVersion = "1.12.0"
            val lifecycleKtxVersion = "2.6.2"
            library("compose-animation", "androidx.compose.animation:animation:$composeVersion")
            library("compose-foundation", "androidx.compose.foundation:foundation:$composeVersion")
            library("compose-material", "androidx.compose.material:material:$composeVersion")
            library("compose-runtime", "androidx.compose.runtime:runtime:$composeVersion")
            library("compose-ui", "androidx.compose.ui:ui:$composeVersion")
            library("compose-ui-graphics", "androidx.compose.ui:ui-graphics:$composeVersion")
            library(
                "compose-ui-tooling-preview",
                "androidx.compose.ui:ui-tooling-preview:$composeVersion"
            )
            library("compose-material3", "androidx.compose.material3:material3:$material3Version")
            library("activity-compose", "androidx.activity:activity-compose:$activityComposeVersion")
            library("core-ktx", "androidx.core:core-ktx:$coreKtxVersion")
            library("lifecycle-runtime", "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleKtxVersion")
            library("compose-ui-tooling", "androidx.compose.ui:ui-tooling:$composeVersion")
            bundle(
                "ui", listOf(
                    "compose-animation",
                    "compose-foundation",
                    "compose-material",
                    "compose-runtime",
                    "compose-ui",
                    "compose-ui-graphics",
                    "compose-ui-tooling-preview",
                    "activity-compose",
                    "core-ktx",
                    "lifecycle-runtime",
                    "compose-material3"
                )
            )
        }
    }
}

rootProject.name = "MultiTool"
include(":app")
