plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("kotlin-parcelize")
  id("dagger.hilt.android.plugin")
}
android {
  signingConfigs {
    getByName("debug") {
      storeFile = file("D:\\Android Studio\\keystore.jks")
      storePassword = AppConfig.KeyStore.password
      keyAlias = AppConfig.KeyStore.alias
      keyPassword = AppConfig.KeyStore.password
    }
    create("release") {
      storeFile = file("D:\\Android Studio\\keystore.jks")
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
      isMinifyEnabled = true
      isShrinkResources = true
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
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  kotlinOptions {
    jvmTarget = "11"
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

  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {

  implementation(Dependencies.AndroidX.implementation)
  implementation(Dependencies.AndroidX.Lifecycle.implementation)
  implementation(Dependencies.AndroidX.Navigation.implementation)
  implementation(Dependencies.Coil.coil)
  implementation(Dependencies.Coroutines.implementation)
  implementation(Dependencies.Hilt.android)
  implementation(Dependencies.Kotlin.stdlib)
  implementation(Dependencies.Material.material)
  implementation(Dependencies.Paging.runtime)

  implementation(Dependencies.SquareUp.implementation)

  kapt(Dependencies.Hilt.compiler)

  debugImplementation(Dependencies.Chucker.debug)
  releaseImplementation(Dependencies.Chucker.release)

  androidTestImplementation(Dependencies.JUnit.junit)
  androidTestImplementation(Dependencies.AndroidX.Test.implementation)
}