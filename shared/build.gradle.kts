import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import ludens.build.compose.configuration.ludensConfiguration
import ludens.build.ios.prebuild.preBuild
import ludens.build.compose.fonts.fontsSync
import ludens.build.compose.language.languageMetadata
import ludens.build.compose.language.languageStringsSync
import ludens.build.compose.resources.filesRes
import ludens.build.compose.resources.icons.appIconGenerator
import ludens.build.compose.resources.resourcesSync
import ludens.build.compose.settings.settingsPreset
import ludens.build.helpers.PluginActivationMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.squareup.wire)
    alias(libs.plugins.google.ksp)
    id("ludens.build")
    alias(libs.plugins.buildKonfig)
}

ludens {
    compose {
        filesRes()
        settingsPreset()
        languageStringsSync {
            mode.set(PluginActivationMode.All)
        }
        languageMetadata()
        fontsSync()
        resourcesSync()
        appIconGenerator {
            name = ludensConfiguration.icons.name
            foreground = ludensConfiguration.icons.foreground
            background = ludensConfiguration.icons.background
            enableAndroid = ludensConfiguration.icons.android.enable
            enablePlaystore = ludensConfiguration.icons.android.playstore
            enableIos = ludensConfiguration.icons.ios.enable
            androidIconFormat = ludensConfiguration.icons.android.resolvedFormat
            iconScale = ludensConfiguration.icons.scale
        }
    }
    ios {
        preBuild {
            enable = ludensConfiguration.ios.build.enable

            context {
                val identity = ludensConfiguration.iosIdentity
                appName = identity.name
                bundleId = identity.id
                marketingVersion = identity.version
                projectVersion = identity.versionCode.toString()
            }
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "ludens.composeapp.generated.resources"
}

buildkonfig {
    packageName = "com.yoimerdr.compose.ludens.konfig.generated"
    exposeObjectWithName = "BuildKonfig"

    defaultConfigs {
        buildConfigField(Type.STRING, "LUDENS_VERSION", "0.4.0")
        buildConfigField(Type.STRING, "LUDENS_WEBSITE_URL", "https://tryludens.vercel.app/")
        buildConfigField(
            Type.STRING,
            "LUDENS_ISSUES_URL",
            "https://github.com/yoimerdr/ludens/issues"
        )
        buildConfigField(
            Type.BOOLEAN,
            "LUDENS_IMMERSIVE",
            ludensConfiguration.android.immersive.toString()
        )
        buildConfigField(
            Type.BOOLEAN,
            "LUDENS_DEBUG_ERRORS",
            ludensConfiguration.debug.errors.toString()
        )
    }
}

kotlin {
    android {
        namespace = "com.yoimerdr.compose.ludens.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = ludensConfiguration.android.minSDK
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        androidResources {
            enable = true
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(compose.uiTooling)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            // Lifecycle
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            // Serialization
            implementation(libs.kotlinx.serialization.json)
            // Navigation
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.ui.backhandler)
            // Compose Components (Joystick, WebView)
            implementation(libs.io.github.yoimerdr.compose.virtualJoystick)
            implementation(libs.io.github.kevinnzou.composeWebview)
            // Injection
            implementation(libs.io.koin.compose)
            implementation(libs.io.koin.compose.viewmodel)
            implementation(libs.io.koin.compose.viewmodel.navigation)
            api(libs.io.koin.annotations)
            // DataStore
            implementation(libs.androidx.datastore.core.okio)
            // Layout
            implementation(libs.sh.calvin.reorderable)
            // Collections
            implementation(libs.kotlinx.collections.immutable)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    // KSP Common sourceSet
    sourceSets.named("commonMain").configure {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }
}


wire {
    kotlin {}

    sourcePath {
        srcDir("src/commonMain/proto")
    }
}

ksp {
    arg("KOIN_GENERATION_PACKAGE", "com.yoimerdr.compose.ludens.koin.generated")
}

dependencies {
    add("kspCommonMainMetadata", libs.io.koin.ksp.compiler)
    add("kspAndroid", libs.io.koin.ksp.compiler)
    add("kspIosArm64", libs.io.koin.ksp.compiler)
    add("kspIosSimulatorArm64", libs.io.koin.ksp.compiler)
}

// Trigger Common Metadata Generation from Native tasks
tasks.matching { it.name.startsWith("ksp") && it.name != "kspCommonMainKotlinMetadata" }
    .configureEach {
        dependsOn("kspCommonMainKotlinMetadata")
    }
