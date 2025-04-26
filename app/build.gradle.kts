plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.enviroscan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.enviroscan"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }



}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.material.v150)

    implementation(libs.circleindicator)

    implementation(libs.whynotimagecarousel)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
//    image slider
//    implementation (libs.imageslideshow)

    // glide  image loader
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //Graph Settings Dependieses

    //MPgraph Barchart
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //loti animation
    implementation ("com.airbnb.android:lottie:4.2.0")

}
