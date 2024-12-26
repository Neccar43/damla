plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

     alias(libs.plugins.hiltAndroid)
     alias(libs.plugins.ksp)
}

android {
    namespace = "com.novacodestudios.damla"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.novacodestudios.damla"
        minSdk = 28
        targetSdk = 35
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
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    //hilt
     implementation(libs.hilt.android)
     ksp(libs.hilt.android.compiler)
     ksp(libs.androidx.hilt.compiler)
     implementation(libs.androidx.hilt.navigation.compose)
    //implementation(libs.androidx.hilt.work)

// Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

// Coroutine Lifecycle Scopes
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

//Coroutine firebase integration
    implementation(libs.kotlinx.coroutines.play.services)

    implementation(libs.coil.compose)

    implementation(libs.androidx.datastore.preferences)


    //implementation("com.google.j2objc:j2objc-annotations:2.8")
    implementation(project(":core:ui"))
    implementation(project(":core:model"))
    implementation(project(":core:data"))

    implementation(project(":feature:appointment"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:donation"))
    implementation(project(":feature:donationcenter"))
    implementation(project(":feature:home"))
    implementation(project(":feature:notification"))
    implementation(project(":feature:profile"))
}