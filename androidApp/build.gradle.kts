import ludens.build.android.configuration.permissions
import ludens.build.android.postbuild.postBuild
import ludens.build.compose.configuration.ludensConfiguration
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    id("ludens.build")
}

ludens {
    android {
        permissions()

        postBuild {
            enable = ludensConfiguration.android.build.enable
            outputDir = ludensConfiguration.android.build.outputDir
            pattern = ludensConfiguration.android.build.pattern
            action = ludensConfiguration.android.build.action
            includeVariants = listOf(ludensConfiguration.android.build.includeVariants)

            context {
                val identity = ludensConfiguration.androidIdentity
                appName = identity.name
                versionName = identity.version
                versionCode = identity.versionCode
                minSdk = ludensConfiguration.android.minSDK
                targetSdk = ludensConfiguration.android.targetSDK
                appId = identity.id
            }
        }
    }
}

android {
    namespace = "com.yoimerdr.compose.ludens"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        val androidIdentity = ludensConfiguration.androidIdentity
        applicationId = androidIdentity.id
        minSdk = ludensConfiguration.android.minSDK
        targetSdk = ludensConfiguration.android.targetSDK
        versionCode = androidIdentity.versionCode
        versionName = androidIdentity.version

        resValue("string", "app_name", androidIdentity.name)
        resValue("string", "app_launcher_name", ludensConfiguration.android.launcherName)

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
            excludes += "composeResources/**"
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
        resValues = true
        compose = true
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.io.koin.compose)
    debugImplementation(compose.uiTooling)
}
