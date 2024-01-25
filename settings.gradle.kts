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
        maven(uri("https://devrepo.kakao.com/nexus/content/groups/public/" ))
    }
}

rootProject.name = "shopping"
include(":app")
include(":data")
include(":domain")
