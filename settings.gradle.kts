pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        maven {

            url = uri("https://maven.cashfree.com/release")
            content {
                includeGroup ("com.cashfree.pg")
            }
        }

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven{
            url = uri("https://maven.cashfree.com/release")

        }

    }
}

rootProject.name = "QuickServe"
include(":app")
include(":Home")
