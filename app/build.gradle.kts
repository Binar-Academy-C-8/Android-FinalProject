plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.raveendra.finalproject_binar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.raveendra.finalproject_binar"
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
    flavorDimensions += "env"
    productFlavors {
        create("production") {
            buildConfigField("String", "BASE_URL", "\"https://backend-production-f9e7.up.railway.app/api/v1/\"")
        }
        create("development") {
            buildConfigField("String", "BASE_URL", "\"https://backend-production-f9e7.up.railway.app/api/v1/\"")
        }
        create("mockup") {
            buildConfigField("String", "BASE_URL", "\"https://backend-production-f9e7.up.railway.app/api/v1/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // firebase

    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    val nav_version = "2.7.2"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Jetpack Navigation

    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // retrofit & okhttp

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")

    // Image

    implementation("io.coil-kt:coil:2.4.0")

    // rv

    implementation("androidx.recyclerview:recyclerview:1.3.1")

    // fragment ktx

    implementation("androidx.fragment:fragment-ktx:1.6.1")

    // room database libraries

    implementation("androidx.room:room-ktx:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")

    // data store

    implementation("androidx.datastore:datastore-preferences:1.0.0")


    // koin

    implementation("io.insert-koin:koin-android:3.5.0")

    // testing

    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("io.mockk:mockk-agent:1.13.8")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")
    testImplementation("app.cash.turbine:turbine:1.0.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    //youtube
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:custom-ui:12.1.0")

    //viewpager
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    //groupie adapter recycleview
    implementation ("com.github.lisawray.groupie:groupie:2.10.1")
    implementation ("com.github.lisawray.groupie:groupie-viewbinding:2.10.1")

    // otp view

    implementation("com.github.mukeshsolanki.android-otpview-pinview:otpview:3.1.0")

    //shimmer
    implementation ("com.facebook.shimmer:shimmer:0.5.0")
    //alert dialog
    implementation ("com.github.GrenderG:Toasty:1.5.2")

    //swipe to refresh list
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")

    //lottie
    implementation ("com.airbnb.android:lottie:6.2.0")
}