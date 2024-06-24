plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.newsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.newsapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.newsapp.HiltTestRunner"

        val newsApiKey: String? = project.findProperty("newsApiKey") as String?
        buildConfigField("String", "NEWS_API_KEY", "\"${newsApiKey}\"")
        buildConfigField("String", "BASE_URL", "\"https://newsapi.org/v2/\"")
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
    buildFeatures {
        buildConfig = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation("androidx.test.uiautomator:uiautomator:2.3.0")
    testImplementation("junit:junit:4.13.2")
    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    implementation(composeBom)
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    //Dagger Hilt
    kapt("com.google.dagger:hilt-android-compiler:2.46")
    implementation("com.google.dagger:hilt-android:2.46")

    //Room DB
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    //LiveData dependency
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.2")

    //Biometric
    implementation("androidx.biometric:biometric:1.2.0-alpha05")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Jetpack Compose
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.material:material:1.6.8")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")

    implementation("org.objenesis:objenesis:3.2")

    //Gson
    implementation("com.google.code.gson:gson:2.11.0")

    androidTestImplementation(composeBom)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:core:1.5.0")

    //hilt
    androidTestAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.46")
    //Compose testing
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.8")
    //Coroutines
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    //Dagger hilt
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")

    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")

    //Coroutine test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    //Mockito
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.mockito:mockito-inline:3.11.2")
    androidTestImplementation("org.mockito:mockito-android:3.12.4")
    testImplementation("org.robolectric:robolectric:4.7.3")

    // Truth dependencies for both unit and instrumented tests
    testImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("androidx.test.ext:truth:1.5.0")

    // MockK for mocking dependencies
    androidTestImplementation("io.mockk:mockk-android:1.12.0")

    //Dagger Hilt Test
    testImplementation("com.google.dagger:hilt-android-testing:2.44")
    // ...with Kotlin.
    kaptTest("com.google.dagger:hilt-android-compiler:2.46")
    // ...with Java.
    testAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.46")

}
// Allow references to generated code
//api key: 2d6ea8a1bad248f59f0de4479e4c881d
kapt {
    correctErrorTypes = true
}