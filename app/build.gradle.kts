plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)

}

android {
    namespace = "com.example.myapplication"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.myapplication.HiltTestRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packaging {
        excludes += "META-INF/LICENSE.md"
        excludes += "META-INF/LICENSE-notice.md"

    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

    dependencies {
        // --- AndroidX & Compose ---
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        implementation(libs.androidx.navigation.runtime.ktx)
        implementation("androidx.navigation:navigation-compose:2.9.3")
        implementation("androidx.compose.material:material-icons-extended:1.6.8")
        implementation(libs.androidx.foundation)
        implementation("androidx.compose.runtime:runtime-livedata:1.8.3")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

        // --- Networking (Retrofit) ---
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

        // --- Dagger Hilt ---
        implementation(libs.dagger.hilt)
        implementation(libs.androidx.runner)
        kapt(libs.dagger.kapt)
        implementation(libs.hilt.compose.navigation)

        // --- Firebase ---
        implementation(platform(libs.firebase.bom))
        implementation(libs.firebase.firestore)
        implementation(libs.firebase.auth)
        implementation(libs.firebase.crashlytics)
        implementation(libs.firebase.analytics)
        implementation("com.google.firebase:firebase-storage-ktx")
        implementation("com.google.android.gms:play-services-auth:21.2.0")
        implementation("com.google.firebase:firebase-messaging")

        // --- Image Loading ---
        implementation("io.coil-kt:coil-compose:2.6.0")

        // --- UI / Material ---
        implementation(libs.material3)
        implementation(libs.ui)
        implementation(libs.androidx.runtime)
        implementation(libs.androidx.junit.ktx)

        // --- Testing ---
        testImplementation(libs.junit)
        testImplementation("io.mockk:mockk:1.13.11")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
        testImplementation("com.google.truth:truth:1.4.2")

        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)

        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation("androidx.test:core:1.5.0")
        androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0")
        androidTestImplementation("io.mockk:mockk-android:1.13.14")
        androidTestImplementation("com.google.truth:truth:1.1.5")

        // --- Hilt Testing ---
        androidTestImplementation("com.google.dagger:hilt-android-testing:2.52")
        kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.52")
        testImplementation("com.google.dagger:hilt-android-testing:2.52")
        kaptTest("com.google.dagger:hilt-android-compiler:2.52")

        // --- Dagger Hilt base ---
        implementation("com.google.dagger:hilt-android:2.52")
        kapt("com.google.dagger:hilt-android-compiler:2.52")

        // --- Hilt Testing (clave para HiltTestApplication) ---
        androidTestImplementation("com.google.dagger:hilt-android-testing:2.52")
        kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.52")

        testImplementation("com.google.dagger:hilt-android-testing:2.52")
        kaptTest("com.google.dagger:hilt-android-compiler:2.52")
    }

