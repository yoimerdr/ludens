/*
 * Copyright 2024 Qamar A. Safadi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * NOTE: This file is a localized port and adaptation of KMPAppIconGeneratorPlugin,
 * integrated into the Ludens project's custom build-logic.
 */

package ludens.build.compose.resources.icons

import com.android.build.api.variant.AndroidComponentsExtension
import ludens.build.LudensComposeExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

// Extension Data Class for Configuration
open class AppIconGeneratorExtension {
    var name: String = "icon"
    var foreground: String = "icon_foreground"
    var background: String = "icon_background"
    var enableAndroid: Boolean = true
    var enableIos: Boolean = true
    var enablePlaystore: Boolean = true
    var androidIconFormat: AndroidIconFormat = AndroidIconFormat.Png
    var iconScale: Double = 0.75
}

class AppIconGeneratorPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // Register the extension
        val config =
            project.extensions.create("appIconGenerator", AppIconGeneratorExtension::class.java)

        val generateIconsTaskProvider =
            project.tasks.register("generateIcons", AppIconGeneratorTask::class.java) {
                group = "ludens"
                description = "Generates Android and iOS icons from a single source image."

                // Wire properties from extension to task
                sourceIconName.set(project.provider { config.name })
                sourceIconForegroundName.set(project.provider { config.foreground })
                sourceIconBackgroundName.set(project.provider { config.background })
                outputAndroid.set(project.provider { config.enableAndroid })
                outputIos.set(project.provider { config.enableIos })
                outputPlaystore.set(project.provider { config.enablePlaystore })
                androidIconFormat.set(project.provider { config.androidIconFormat })
                iconScale.set(project.provider { config.iconScale })
                rootDir.set(project.rootDir)
                commonResourcesDir.set(project.rootProject.layout.projectDirectory.dir("project/assets/icons"))
                androidResDir.set(project.rootProject.layout.projectDirectory.dir("composeApp/src/androidMain/res"))
                iosAppIconSetDir.set(project.rootProject.layout.projectDirectory.dir("iosApp/iosApp/Assets.xcassets/AppIcon.appiconset"))
                playstoreIconFile.set(project.rootProject.layout.projectDirectory.file("composeApp/src/androidMain/ic_launcher-playstore.png"))
            }

        project.pluginManager.withPlugin("com.android.application") {
            project.extensions.getByType(AndroidComponentsExtension::class.java)
                .onVariants { variant ->
                    val variantName =
                        variant.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

                    project.tasks.matching {
                        it.name in listOf(
                            "merge${variantName}Resources",
                            "package${variantName}Resources",
                            "process${variantName}Resources",
                            "assemble${variantName}"
                        )
                    }.configureEach {
                        dependsOn(generateIconsTaskProvider)
                    }
                }
        }
        project.plugins.withType(KotlinMultiplatformPluginWrapper::class.java) {
            project.extensions.findByType(KotlinMultiplatformExtension::class.java)?.targets?.withType(
                KotlinNativeTarget::class.java
            )?.configureEach {
                binaries.all { linkTaskProvider.configure { dependsOn(generateIconsTaskProvider) } }
            }
        }
    }
}

/**
 * Applies and configures the KMP App Icon Generator Plugin.
 */
fun LudensComposeExtension.appIconGenerator(configure: AppIconGeneratorExtension.() -> Unit = {}) {
    project.pluginManager.apply(AppIconGeneratorPlugin::class.java)
    project.extensions.configure(AppIconGeneratorExtension::class.java, configure)
}
