import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import ludens.build.compose.configuration.ludensConfiguration
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.google.protobuf)
    alias(libs.plugins.squareup.wire)
    alias(libs.plugins.google.ksp)
    id("ludens.build.compose.resources.files")
    id("ludens.build.compose.settings.preset")
    id("ludens.build.android.permissions.manifest")
    alias(libs.plugins.buildKonfig)
}

// Using the full extension type to bypass the "unresolved reference" accessor issue
buildkonfig {
    packageName = "com.yoimerdr.compose.ludens.konfig.generated"

    defaultConfigs {
        buildConfigField(Type.STRING, "LUDENS_VERSION", "0.2.0")
        buildConfigField(Type.STRING, "LUDENS_WEBSITE_URL", "https://github.com/yoimerdr/ludens")
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
    }
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
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

android {
    namespace = "com.yoimerdr.compose.ludens"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        // Core app identity comes from `ludens.properties`.
        applicationId = ludensConfiguration.android.id
        minSdk = ludensConfiguration.android.minSDK
        targetSdk = ludensConfiguration.android.targetSDK
        versionCode = ludensConfiguration.android.versionCode
        versionName = ludensConfiguration.android.version

        resValue("string", "app_name", ludensConfiguration.android.name)
        resValue("string", "app_launcher_name", ludensConfiguration.android.launcherName)

        // Manifest placeholders are resolved in `src/androidMain/AndroidManifest.xml`.
        manifestPlaceholders["ludensAllowBackup"] = ludensConfiguration.android.manifest.allowBackup
        manifestPlaceholders["ludensLargeHeap"] = ludensConfiguration.android.manifest.largeHeap
        manifestPlaceholders["ludensHardwareAccelerated"] =
            ludensConfiguration.android.manifest.hardwareAccelerated
        manifestPlaceholders["ludensScreenOrientation"] =
            ludensConfiguration.android.manifest.screenOrientation
        manifestPlaceholders["ludensUsesCleartextTraffic"] =
            ludensConfiguration.android.manifest.usesCleartextTraffic
        manifestPlaceholders["ludensResizeableActivity"] =
            ludensConfiguration.android.manifest.resizeableActivity
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        create("release") {
            val keystorePropertiesFile = rootProject.file("keystore.properties")
            if (keystorePropertiesFile.exists()) {
                val properties = Properties()
                properties.load(FileInputStream(keystorePropertiesFile))

                storeFile = rootProject.file(properties.getProperty("storeFile"))
                storePassword = properties.getProperty("storePassword")
                keyAlias = properties.getProperty("keyAlias")
                keyPassword = properties.getProperty("keyPassword")
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
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
    debugImplementation(compose.uiTooling)
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
