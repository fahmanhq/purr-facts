plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "app.purrfacts.core.testing.android"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(libs.androidx.junit)
    api(libs.androidx.espresso.core)
    api(libs.truth)

    api(libs.hilt.android.testing)

    implementation(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui.test.junit4)
    api(libs.androidx.ui.test.android)
}