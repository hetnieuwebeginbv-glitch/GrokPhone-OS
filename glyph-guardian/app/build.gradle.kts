plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ai.stay4safe.glyph"
    compileSdk = 35

    defaultConfig {
        applicationId = "ai.stay4safe.glyph"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
        // Nothing Glyph SDK — debug token voor development
        manifestPlaceholders["nothingApiKey"] = System.getenv("NOTHING_API_KEY") ?: "test"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug { isDebuggable = true }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    // Nothing Glyph SDK — lokaal AAR bestand
    implementation(files("../libs/GlyphSDK.aar"))
    testImplementation("junit:junit:4.13.2")
}
