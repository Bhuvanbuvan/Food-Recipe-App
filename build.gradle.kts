// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
    }
    dependencies {
        fun nav_version() = "2.7.7"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${nav_version()}")
        classpath("com.google.gms:google-services:4.4.1")
    }
}


plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}