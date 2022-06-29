buildscript {
  repositories {
    google()
    mavenCentral()
  }

  dependencies {
    classpath("com.android.tools.build:gradle:${Version.toolsBuildGradle}")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinGradle}")
    classpath("com.google.dagger:hilt-android-gradle-plugin:${Version.hiltGradle}")
  }
}

subprojects {
  repositories {
    google()
    mavenCentral()
    maven { url = uri("https://www.jitpack.io") }
    maven {
      url = uri("https://jitpack.io")
      credentials {
        username = "jp_uvj8t2utirothfevp7gb6l9udn"
      }
    }
  }
}