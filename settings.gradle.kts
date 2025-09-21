pluginManagement {
    repositories {
        google()               // ✅ Bắt buộc để tìm thấy Flexbox
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()               // ✅ Bắt buộc
        mavenCentral()         // ✅ Bắt buộc
    }
}

rootProject.name = "Facebook"
include(":app")
