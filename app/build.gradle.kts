plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.bitfit02"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bitfit02"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true
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
}

dependencies {

    // Update to Kotlin 1.9.0 (or your desired version)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0") // Ensure the Kotlin stdlib is updated
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0") // If you use JDK8 features

    // AndroidX libraries
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.codepath.libraries:asynchttpclient:2.2.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Room Database
    implementation("androidx.room:room-runtime:2.4.2")
    implementation("androidx.room:room-ktx:2.4.2")
    annotationProcessor("androidx.room:room-compiler:2.4.2")
    kapt("androidx.room:room-compiler:2.4.2")

    // Fragment and other libraries
    implementation("androidx.fragment:fragment-ktx:1.5.0")
}