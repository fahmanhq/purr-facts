plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "app.purrfacts.core.testing.android"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        getByName("main") {
            java.srcDirs()
            res.srcDirs()
        }
    }
}

dependencies {
    api(libs.androidx.junit)
    api(libs.androidx.espresso.core)
    api(libs.truth)

    implementation(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui.test.junit4)
    api(libs.androidx.ui.test.android)
}