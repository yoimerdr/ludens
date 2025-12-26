import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.google.protobuf)
    alias(libs.plugins.squareup.wire)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
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
            // DataStore
            implementation(libs.androidx.datastore.core.okio)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.yoimerdr.compose.ludens"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.yoimerdr.compose.ludens"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

wire {
    kotlin {}

    sourcePath {
        srcDir("src/commonMain/proto")
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}
