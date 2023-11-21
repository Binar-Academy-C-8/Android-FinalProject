buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        classpath("com.android.tools.build:gradle:7.3.0")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.2" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1" apply false
}