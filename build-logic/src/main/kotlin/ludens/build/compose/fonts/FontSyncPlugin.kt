package ludens.build.compose.fonts

import ludens.build.compose.configuration.ludensConfiguration
import ludens.build.helpers.composeGenerationDir
import ludens.build.helpers.composeKotlinSourceSet
import ludens.build.helpers.composeResourcesDir
import ludens.build.helpers.assetsStoreDir
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Registers the font sync task and wires it into Kotlin compilation.
 *
 * The plugin copies font assets into Compose resources, generates `FontFamily` accessors, and
 * ensures the generated source is available to `commonMain` before Kotlin compilation. It relies
 * on the current Ludens configuration to know which font names and language fallbacks should be
 * materialized.
 */
class FontSyncPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val config = ludensConfiguration
            val syncTask = tasks.register<FontSyncTask>("ludensFontSync") {
                assetsFontsDir.set(assetsStoreDir.resolve("fonts"))
                assetsLanguagesDir.set(assetsStoreDir.resolve("languages"))
                resourceFontsDir.set(composeResourcesDir.resolve("font"))
                generatedFile.set(
                    composeGenerationDir
                        .resolve("resources")
                        .resolve("Font.kt")
                )
                generatedPackage.set("com.yoimerdr.compose.ludens.generated.resources")
                configuration = config
                group = "ludens"
                description = "Syncs font files and generates FontFamily composables."
            }

            afterEvaluate {
                composeKotlinSourceSet?.kotlin
                    ?.srcDir(syncTask.map { it.generatedFile.get().asFile.parentFile })
            }

            tasks.withType(KotlinCompile::class.java).configureEach {
                dependsOn(syncTask)
            }
        }
    }
}
