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
    implementation(libs.jackson.dataformat.properties)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.svg.salamander)
    implementation(libs.webp.imageio)
}

gradlePlugin {
    plugins {
        register("ludensBuild") {
            id = "ludens.build"
            implementationClass = "ludens.build.LudensBuildPlugin"
        }
        register("composeGenerateFileRes") {
            id = "ludens.build.compose.resources.files"
            implementationClass = "ludens.build.compose.resources.ComposeResourceFilesPlugin"
        }
        register("ludensComposeGenerateSettingsPreset") {
            id = "ludens.build.compose.settings.preset"
            implementationClass = "ludens.build.compose.settings.SettingsPresetPlugin"
        }
        register("ludensLanguageMetadata") {
            id = "ludens.build.compose.language.metadata"
            implementationClass = "ludens.build.compose.language.LanguageMetadataPlugin"
        }
        register("ludensPermissionsManifest") {
            id = "ludens.build.android.permissions.manifest"
            implementationClass = "ludens.build.android.configuration.PermissionsManifestPlugin"
        }
        register("ludensLanguageStringsSync") {
            id = "ludens.build.compose.language.strings.sync"
            implementationClass = "ludens.build.compose.language.LanguageStringsSyncPlugin"
        }
        register("ludensFontSync") {
            id = "ludens.build.compose.fonts.sync"
            implementationClass = "ludens.build.compose.fonts.FontSyncPlugin"
        }
        register("ludensWebAssetsSync") {
            id = "ludens.build.compose.resources.sync"
            implementationClass = "ludens.build.compose.resources.WebAssetsSyncPlugin"
        }
        register("ludensAppIconGenerator") {
            id = "ludens.build.compose.icons.generator"
            implementationClass = "ludens.build.compose.resources.icons.AppIconGeneratorPlugin"
        }
    }
}
