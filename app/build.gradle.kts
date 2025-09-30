plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.practica.exchangecrypto"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.practica.exchangecrypto"
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    val navVersion = "2.7.1"
    val daggerHilt = "2.48"
    val retroGson = "2.9.0"
    //val moshi = "1.15.0"

    //NavComponent
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retroGson")
    implementation("com.squareup.retrofit2:converter-gson:$retroGson")

    //DaggerHilt
    implementation("com.google.dagger:hilt-android:$daggerHilt")
    kapt("com.google.dagger:hilt-compiler:$daggerHilt")

    //Coil
    implementation("io.coil-kt:coil:2.4.0") // load image

    // Moshi
    //implementation("com.squareup.moshi:moshi:$moshi")
    //implementation("com.squareup.moshi:moshi-kotlin:$moshi")

    // to automatically generate the adapter with @JsonClass(generateAdapter = true)
    //kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshi")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}