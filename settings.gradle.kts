pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CommonPregnancy"
include(":app")
include(":core")
include(":data")
include(":domain")
include(":feature:advices")
include(":feature:tasks")
include(":feature:settings")
