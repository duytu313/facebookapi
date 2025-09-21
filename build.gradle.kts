// Root build.gradle.kts
plugins {
    // Không cần kotlin("android") ở root, chỉ module apply
    id("com.android.application") version "8.13.0" apply false
    id("com.android.library") version "8.13.0" apply false
    kotlin("android") version "1.9.10" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

