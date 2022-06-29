import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

  object AndroidX {
    private const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
    private const val splashScreen = "androidx.core:core-splashscreen:${Version.coreSplashScreen}"
    private const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Version.swipeRefreshLayout}"

    val implementation = arrayListOf<String>().apply {
      add(coreKtx)
      add(splashScreen)
      add(swipeRefreshLayout)
    }

    object Lifecycle {
      private const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle}"
      private const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycle}"

      val implementation = arrayListOf<String>().apply {
        add(viewModel)
        add(runtime)
      }
    }

    object Navigation {
      private const val activity = "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
      private const val fragment = "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"

      val implementation = arrayListOf<String>().apply {
        add(activity)
        add(fragment)
      }
    }

    object Test {
      private const val core = "androidx.test:core:${Version.jUnit}"
      private const val rules = "androidx.test:rules:${Version.jUnit}"
      private const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"

      val implementation = arrayListOf<String>().apply {
        add(core)
        add(rules)
        add(espressoCore)
      }
    }
  }

  object Chucker {
    const val debug = "com.github.chuckerteam.chucker:library:${Version.chucker}"
    const val release = "com.github.chuckerteam.chucker:library-no-op:${Version.chucker}"
  }

  object Coil {
    const val coil = "io.coil-kt:coil:${Version.coil}"
  }

  object Coroutines {
    private const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
    private const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"

    val implementation = arrayListOf<String>().apply {
      add(android)
      add(core)
    }
  }

  object Hilt {
    const val android = "com.google.dagger:hilt-android:${Version.hilt}"
    const val compiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
  }

  object  JUnit {
    const val junit = "junit:junit:${Version.jUnit}"
  }

  object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}"
  }

  object Material {
    const val material = "com.google.android.material:material:${Version.materialDesign}"
  }

  object Paging {
    const val runtime = "androidx.paging:paging-runtime:${Version.paging}"
  }

  object SquareUp {

    object Moshi {
      const val kotlin = "com.squareup.moshi:moshi-kotlin:${Version.moshi}"
    }

    object OkHttp {
      const val logging = "com.squareup.okhttp3:logging-interceptor:${Version.okHttp}"
    }

    object Retrofit {
      const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Version.retrofit}"
    }

    val implementation = arrayListOf<String>().apply {
      add(Moshi.kotlin)
      add(OkHttp.logging)
      add(Retrofit.converterMoshi)
    }

  }
}

fun DependencyHandler.kapt(list: List<String>) {
  list.forEach { dependency ->
    add("kapt", dependency)
  }
}

fun DependencyHandler.implementation(list: List<String>) {
  list.forEach { dependency ->
    add("implementation", dependency)
  }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
  list.forEach { dependency ->
    add("androidTestImplementation", dependency)
  }
}

fun DependencyHandler.debugImplementation(list: List<String>) {
  list.forEach { dependency ->
    add("debugImplementation", dependency)
  }
}

fun DependencyHandler.releaseImplementation(list: List<String>) {
  list.forEach { dependency ->
    add("releaseImplementation", dependency)
  }
}