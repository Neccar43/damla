pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Damla"
include(":app")
include(":core:ui")
include(":core:data")
include(":core:model")
include(":feature:auth")
include(":feature:home")
include(":feature:notification")
include(":feature:appointment")
include(":feature:donation")
include(":feature:profile")
include(":core:network")
include(":core:datastore")
include(":feature:donationcenter")
include(":core:util")
