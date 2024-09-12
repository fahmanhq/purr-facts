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
rootProject.name = "purr-facts"

include(":app")
include(":domain")
include(":database")
include(":data:api")
include(":data:impl")
include(":network")
include(":core:ui")
include(":feature:factforyou")
