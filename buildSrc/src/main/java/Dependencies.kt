import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    object AndroidX {
        private const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
        private const val fragmentKtx = "androidx.fragment:fragment-ktx:${Version.fragmentKtx}"
        private const val splashScreen =
            "androidx.core:core-splashscreen:${Version.coreSplashScreen}"
        private const val swipeRefreshLayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Version.swipeRefreshLayout}"

        val implementation = arrayListOf<String>().apply {
            add(coreKtx)
            add(fragmentKtx)
            add(splashScreen)
            add(swipeRefreshLayout)
        }

        object Lifecycle {
            private const val viewModel =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle}"
            private const val runtime =
                "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycle}"

            val implementation = arrayListOf<String>().apply {
                add(viewModel)
                add(runtime)
            }
        }

        object Navigation {
            private const val activity =
                "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
            private const val fragment =
                "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"

            val implementation = arrayListOf<String>().apply {
                add(activity)
                add(fragment)
            }
        }

        object Test {
            private const val core = "androidx.test:core:${Version.jUnit}"
            private const val rules = "androidx.test:rules:${Version.jUnit}"
            private const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"

            val androidTestImplementation = arrayListOf<String>().apply {
                add(core)
                add(rules)
                add(espressoCore)
            }
        }
    }

    object Chucker {
        private const val debug = "com.github.chuckerteam.chucker:library:${Version.chucker}"
        private const val release =
            "com.github.chuckerteam.chucker:library-no-op:${Version.chucker}"

        val debugImplementation = arrayListOf<String>().apply {
            add(debug)
        }

        val releaseImplementation = arrayListOf<String>().apply {
            add(release)
        }
    }

    object Coil {
        private const val coil = "io.coil-kt:coil:${Version.coil}"

        val implementation = arrayListOf<String>().apply {
            add(coil)
        }
    }

    object Coroutines {
        private const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
        private const val core =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"

        val implementation = arrayListOf<String>().apply {
            add(android)
            add(core)
        }
    }

    object Hilt {
        private const val android = "com.google.dagger:hilt-android:${Version.hilt}"
        private const val compiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"

        val implementation = arrayListOf<String>().apply {
            add(android)
        }

        val kapt = arrayListOf<String>().apply {
            add(compiler)
        }
    }

    object Insetter {
        private const val insetter = "dev.chrisbanes.insetter:insetter:${Version.insetter}"

        val implementation = arrayListOf<String>().apply {
            add(insetter)
        }
    }

    object JUnit {
        private const val junit = "junit:junit:${Version.jUnit}"

        val androidTestImplementation = arrayListOf<String>().apply {
            add(junit)
        }
    }

    object Kotlin {
        private const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}"

        val implementation = arrayListOf<String>().apply {
            add(stdlib)
        }
    }

    object Material {
        private const val material =
            "com.google.android.material:material:${Version.materialDesign}"

        val implementation = arrayListOf<String>().apply {
            add(material)
        }
    }

    object Paging {
        private const val runtime = "androidx.paging:paging-runtime:${Version.paging}"

        val implementation = arrayListOf<String>().apply {
            add(runtime)
        }
    }

    object Service {

        private const val kotlin = "com.squareup.moshi:moshi-kotlin:${Version.moshi}"
        private const val logging = "com.squareup.okhttp3:logging-interceptor:${Version.okHttp}"
        private const val converterMoshi =
            "com.squareup.retrofit2:converter-moshi:${Version.retrofit}"

        val implementation = arrayListOf<String>().apply {
            add(kotlin)
            add(logging)
            add(converterMoshi)
        }
    }

    object Shimmer {
        private const val shimmer = "com.facebook.shimmer:shimmer:${Version.shimmer}"

        val implementation = arrayListOf<String>().apply {
            add(shimmer)
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