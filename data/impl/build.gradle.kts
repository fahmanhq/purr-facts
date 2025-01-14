plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.hilt.android)
    kotlin("kapt")
}

android {
    namespace = "app.purrfacts.data.impl"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    api(project(":data:api"))

    implementation(project(":core:common"))
    implementation(project(":database"))
    implementation(project(":network"))

    // Hilt
    implementation(libs.hilt)
    kapt(libs.hilt.android.compiler)

    testImplementation(project(":core:testing"))
    testImplementation(project(":data:testing"))
    androidTestImplementation(project(":core:testing-android"))
}