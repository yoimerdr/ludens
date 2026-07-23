package ludens.build.android.postbuild

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import ludens.build.LudensAndroidExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import java.io.File

/**
 * Gradle plugin for automatic post-build Android artifact renaming and relocation.
 *
 * Registers the [PostBuildExtension] under `postBuild` and wires variant-aware
 * [AndroidBuildPostProcessTask] instances lazily after Android packaging/assembly.
 *
 * **Note:** In compliance with the build-system architecture, this plugin class does not
 * reference `ludensConfiguration` directly inside its implementation. Properties are passed
 * from the consuming module build script via [PostBuildExtension].
 */
class AndroidBuildPostProcessPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val config = project.extensions.create("postBuild", PostBuildExtension::class.java)

        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        androidComponents.onVariants { variant ->
            if (!config.enable) return@onVariants

            val variantName = variant.name
            // Split comma-separated variants list and trim whitespace (e.g. "release,debug" -> ["release", "debug"])
            val includedVariants =
                config.includeVariants.flatMap { it.split(",") }.map { it.trim() }

            val isVariantIncluded =
                includedVariants.contains("all") || includedVariants.contains(variantName)

            if (!isVariantIncluded) return@onVariants

            val capitalizedVariant = variantName.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            }

            fun registerPostProcessTask(
                taskName: String,
                taskDescription: String,
                targetFileType: String,
                configureInputs: AndroidBuildPostProcessTask.() -> Unit,
            ) = project.tasks.register<AndroidBuildPostProcessTask>(taskName) {
                group = "ludens"
                description = taskDescription

                this.variantName.set(variantName)
                this.targetFileType.set(targetFileType)
                this.requestedTasks.set(project.provider { project.gradle.startParameter.taskNames })
                this.appName.set(project.provider { config.context.appName })
                this.versionName.set(project.provider { config.context.versionName })
                this.versionCode.set(project.provider { config.context.versionCode })
                this.minSdk.set(project.provider { config.context.minSdk })
                this.targetSdk.set(project.provider { config.context.targetSdk })
                this.appId.set(project.provider { config.context.appId })
                this.pattern.set(project.provider { config.pattern })
                this.actionMode.set(project.provider { config.action })

                val resolvedOutputDir = config.outputDir.replace("{buildType}", variantName)
                    .replace("{variant}", variantName)

                val outputDirFile = File(resolvedOutputDir)
                if (outputDirFile.isAbsolute) {
                    this.targetOutputDir.fileValue(outputDirFile)
                } else {
                    this.targetOutputDir.set(
                        project.rootProject.layout.projectDirectory.dir(
                            resolvedOutputDir
                        )
                    )
                }

                this.moduleVariantDir.set(project.layout.projectDirectory.dir(variantName))

                configureInputs()
            }

            val apkPostProcessTask = registerPostProcessTask(
                taskName = "ludensPostBuild${capitalizedVariant}Apk",
                taskDescription = "Renames and relocates compiled Android APK artifacts for $variantName variant.",
                targetFileType = "apk"
            ) {
                this.inputApkDir.set(variant.artifacts.get(SingleArtifact.APK))
            }

            val bundlePostProcessTask = registerPostProcessTask(
                taskName = "ludensPostBuild${capitalizedVariant}Bundle",
                taskDescription = "Renames and relocates compiled Android AAB artifacts for $variantName variant.",
                targetFileType = "aab"
            ) {
                this.inputBundleDir.set(project.layout.buildDirectory.dir("outputs/bundle/$variantName"))
            }

            val apkBuildTasks = project.tasks.matching {
                it.name == "package${capitalizedVariant}" || it.name == "package${capitalizedVariant}Apk" || it.name == "assemble${capitalizedVariant}"
            }
            apkBuildTasks.configureEach { finalizedBy(apkPostProcessTask) }
            apkPostProcessTask.configure { mustRunAfter(apkBuildTasks) }

            val bundleBuildTasks = project.tasks.matching {
                it.name == "package${capitalizedVariant}Bundle" || it.name == "sign${capitalizedVariant}Bundle" || it.name == "bundle${capitalizedVariant}"
            }
            bundleBuildTasks.configureEach { finalizedBy(bundlePostProcessTask) }
            bundlePostProcessTask.configure { mustRunAfter(bundleBuildTasks) }
        }
    }
}

/**
 * Applies and configures the [AndroidBuildPostProcessPlugin].
 *
 * Usage in `composeApp/build.gradle.kts`:
 * ```kotlin
 * ludens {
 *     android {
 *         postBuild {
 *             enable = ludensConfiguration.android.build.enable
 *             outputDir = ludensConfiguration.android.build.outputDir
 *             pattern = ludensConfiguration.android.build.pattern
 *             action = ludensConfiguration.android.build.action
 *             includeVariants = listOf(ludensConfiguration.android.build.includeVariants)

 *             context {
 *                 appName = ludensConfiguration.android.name
 *                 versionName = ludensConfiguration.android.version
 *                 versionCode = ludensConfiguration.android.versionCode
 *                 minSdk = ludensConfiguration.android.minSDK
 *                 targetSdk = ludensConfiguration.android.targetSDK
 *                 appId = ludensConfiguration.android.id
 *             }
 *         }
 *     }
 * }
 * ```
 *
 * @param configure Configuration lambda applied to [PostBuildExtension].
 */
fun LudensAndroidExtension.postBuild(configure: PostBuildExtension.() -> Unit = {}) {
    project.pluginManager.apply(AndroidBuildPostProcessPlugin::class.java)
    project.extensions.configure(PostBuildExtension::class.java, configure)
}
