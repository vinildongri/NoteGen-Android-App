plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // 👇 ADD THIS LINE
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.example.notegen"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.notegen"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

//    dependence for Icons
    implementation("androidx.compose.material:material-icons-extended:1.6.6")

//    dependence for Novigation
    val navigationVersion = "2.7.7"
    implementation("androidx.navigation:navigation-compose:${navigationVersion}")

//    Retrofit
    val retrofitVersion = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
//    Gson Converters
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

//    MarkdownColors
    // Add this official Maven Central version
    implementation("com.mikepenz:multiplatform-markdown-renderer-android:0.27.0")

//    user Preferences
    val preferencesVersion = "1.1.0"
    implementation("androidx.datastore:datastore-preferences:$preferencesVersion")

//    Security Crypto
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // OkHttp (needed for 'client')
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

}