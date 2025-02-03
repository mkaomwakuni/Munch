import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.est.munchy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.est.munchy"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Hilt for Dependency Injection
    implementation (libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt (libs.hilt.compiler)

    // Timber for logging
    implementation (libs.timber)

    // Retrofit for Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // OkHttp for HTTP Client
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Performance
    debugImplementation(libs.leak.canary)
    implementation(libs.timber)

    // Room for Local Database
    implementation (libs.androidx.room.ktx)
    implementation(libs.room.runtime)
    kapt ("androidx.room:room-compiler:2.6.1")

    //Preference DataStore
    implementation(libs.androidx.datastore.preferences)

    // Coroutines for Asynchronous Programming
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Navigation
    implementation(libs.navigation.compose)

    //Coil
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("io.coil-kt:coil-gif:2.5.0")
    implementation("io.coil-kt:coil-svg:2.5.0")

    // Gson
    implementation (libs.gson)

    //Animations
    implementation (libs.lottie)

    // Jsoup for parse
    implementation (libs.jsoup)

    //Swipe
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.31.3-beta")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}