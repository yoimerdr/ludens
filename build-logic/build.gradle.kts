plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("composeGenerateFileRes") {
            id = "ludens.build.compose.resources.files"
            implementationClass = "ludens.build.compose.resources.ComposeResourceFilesPlugin"
        }
    }
}
