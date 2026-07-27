package ludens.build.ios.prebuild

import ludens.build.LudensIosExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

/**
 * Gradle plugin for automatic iOS pre-build metadata sync.
 *
 * Registers the [PreBuildExtension] under `preBuild` and wires a single shared
 * [IosBuildPreProcessTask] as a dependency of every iOS framework link task.
 *
 */
class IosBuildPreProcessPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val config = project.extensions.create("preBuild", PreBuildExtension::class.java)

        if (!config.enable) return

        val iosPreBuildTaskProvider =
            project.tasks.register("iosPreBuild", IosBuildPreProcessTask::class.java) {
                group = "ludens"
                description = "Pre-processes Config.xcconfig with Ludens metadata before iOS framework link."

                appName.set(project.provider { config.context.appName })
                bundleId.set(project.provider { config.context.bundleId })
                marketingVersion.set(project.provider { config.context.marketingVersion })
                projectVersion.set(project.provider { config.context.projectVersion })
                targetConfigFile.set(
                    project.rootProject.layout.projectDirectory.file(config.configFile)
                )
            }

        project.plugins.withType(KotlinMultiplatformPluginWrapper::class.java) {
            project.extensions.findByType(KotlinMultiplatformExtension::class.java)?.targets?.withType(
                KotlinNativeTarget::class.java
            )?.configureEach {
                binaries.all { linkTaskProvider.configure { dependsOn(iosPreBuildTaskProvider) } }
            }
        }
    }
}

/**
 * Applies and configures the [IosBuildPreProcessPlugin].
 *
 * Usage in `composeApp/build.gradle.kts`:
 * ```kotlin
 * ludens {
 *     ios {
 *         preBuild {
 *             enable = ludensConfiguration.ios.build.enable
 *             context {
 *                 val identity = ludensConfiguration.resolvedIosIdentity
 *                 appName = identity.name
 *                 bundleId = identity.id
 *                 marketingVersion = identity.version
 *                 projectVersion = identity.versionCode.toString()
 *             }
 *         }
 *     }
 * }
 * ```
 *
 * @param configure Configuration lambda applied to [PreBuildExtension].
 */
fun LudensIosExtension.preBuild(configure: PreBuildExtension.() -> Unit = {}) {
    project.pluginManager.apply(IosBuildPreProcessPlugin::class.java)
    project.extensions.configure(PreBuildExtension::class.java, configure)
}
