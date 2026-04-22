package ludens.build.compose.settings

import ludens.build.compose.configuration.ludensConfiguration
import ludens.build.compose.configuration.resolvedSettingsPreset
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Registers the task that generates `LudensSettingsPreset.kt` from `ludens.properties`.
 *
 * The plugin resolves the Ludens configuration once, wires the generation task into the KMP
 * source set and makes all Kotlin compile tasks depend on the generated file.
 */
class SettingsPresetPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val preset = ludensConfiguration.resolvedSettingsPreset()

            val generateTask = tasks.register<SettingsPresetGenerationTask>("generateLudensSettingsPreset") {
                packageName.set("com.yoimerdr.compose.ludens.generated.settings")
                objectName.set("LudensSettingsPreset")

                presetName.set(preset.presetName)
                toolMuted.set(preset.toolMuted)
                toolShowFPS.set(preset.toolShowFps)
                toolUseWebgl.set(preset.toolUseWebgl)
                controlEnabled.set(preset.controlEnabled)
                controlAlpha.set(preset.controlAlpha)
                actionEnabled.set(preset.actionEnabled)
                actionItems.set(preset.actionItems)
                systemTheme.set(preset.systemTheme)
                systemLanguage.set(preset.systemLanguage)

                outputFile.set(
                    layout.buildDirectory.file(
                        "generated/ludens/compose/settings/LudensSettingsPreset.kt"
                    )
                )
            }

            afterEvaluate {
                extensions.findByType(KotlinMultiplatformExtension::class.java)
                    ?.sourceSets
                    ?.findByName("commonMain")
                    ?.kotlin
                    ?.srcDir(generateTask.map { it.outputFile.get().asFile.parentFile })
            }

            tasks.withType(KotlinCompile::class.java)
                .configureEach {
                    dependsOn(generateTask)
                }
        }
    }
}
