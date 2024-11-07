plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.diffplug.spotless")
    id("androidx.navigation.safeargs.kotlin")
}

android {

    namespace = "com.dirzaaulia.footballclips"

    signingConfigs {
        create("release") {
            storeFile = file("D:\\Keystore\\keystore.jks")
            storePassword = AppConfig.KeyStore.password
            keyAlias = AppConfig.KeyStore.alias
            keyPassword = AppConfig.KeyStore.password
        }
    }

    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.dirzaaulia.footballclips"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = AppConfig.testInstrumentationRunner
    }

    buildTypes {
        getByName("debug") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true

        // Disable unused AGP features
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.AndroidX.implementation)
    implementation(Dependencies.AndroidX.Lifecycle.implementation)
    implementation(Dependencies.AndroidX.Navigation.implementation)
    implementation(Dependencies.Coil.implementation)
    implementation(Dependencies.Coroutines.implementation)
    implementation(Dependencies.Hilt.implementation)
    implementation(Dependencies.Insetter.implementation)
    implementation(Dependencies.Kotlin.implementation)
    implementation(Dependencies.Material.implementation)
    implementation(Dependencies.Paging.implementation)
//    implementation(Dependencies.PlayServices.implementation)
    implementation(Dependencies.Service.implementation)
    implementation(Dependencies.Shimmer.implementation)

    kapt(Dependencies.Hilt.kapt)

    debugImplementation(Dependencies.Chucker.debugImplementation)
    releaseImplementation(Dependencies.Chucker.releaseImplementation)

    androidTestImplementation(Dependencies.JUnit.androidTestImplementation)
    androidTestImplementation(Dependencies.AndroidX.Test.androidTestImplementation)
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        ktlint()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}
