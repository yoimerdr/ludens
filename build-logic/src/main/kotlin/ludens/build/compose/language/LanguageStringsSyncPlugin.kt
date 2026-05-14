package ludens.build.compose.language

import ludens.build.compose.configuration.ludensConfiguration
import ludens.build.helpers.PluginActivationMode
import ludens.build.helpers.assetsStoreDir
import ludens.build.helpers.composeResourcesDir
import ludens.build.helpers.isPluginEnabled
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Registers the language strings sync task and wires it into Kotlin compilation.
 *
 * The plugin keeps Compose resources in sync with the language asset store before Kotlin source
 * compilation runs.
 *
 * Recommended workflow:
 * - keep the language configuration aligned for all supported locales
 * - when adding a new language, update both the original `composeResources/values*` files and
 *   the matching asset store files so the sync task can discover and copy it correctly
 * - for no overwrite developer changes, update activationMode to `ReleaseOnly` and run a debug build only.
 */
class LanguageStringsSyncPlugin : Plugin<Project> {
    /**
     * Applies the language synchronization convention to the target project.
     */
    override fun apply(target: Project) {
        with(target) {
            val activationMode = PluginActivationMode.ReleaseOnly
            if (!isPluginEnabled(activationMode)) return

            /**
             * Task that syncs translated `strings.xml` files into Compose resources.
             */
            val syncTask = tasks.register<LanguageStringsSyncTask>("ludensLanguageStringsSync") {
                assetLanguagesDir.set(assetsStoreDir.resolve("languages"))
                resourcesDir.set(composeResourcesDir)
                configuration.set(ludensConfiguration.languages)
                group = "ludens"
                description = "Syncs language strings.xml from asset store to Compose resources."
            }

            tasks.withType(KotlinCompile::class.java).configureEach {
                dependsOn(syncTask)
            }
        }
    }
}
