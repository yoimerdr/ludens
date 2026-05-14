package ludens.build.compose.resources

import ludens.build.helpers.composeResourcesFilesDir
import ludens.build.helpers.projectWwwDir
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Sync
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Registers the web assets synchronization task.
 *
 * This plugin synchronizes the contents of `project/www` into `composeResources/files/www`.
 * It will ONLY execute (copying and mirroring files) if `project/www` contains more than just
 * the default `index.html` file. If only `index.html` is present, it does nothing, allowing
 * developers to use `composeResources/files/www` directly.
 */
class WebAssetsSyncPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val wwwDir = projectWwwDir
            val destDir = composeResourcesFilesDir.resolve("www")

            val syncTask = tasks.register<Sync>("ludensWebAssetsSync") {
                from(wwwDir)
                into(destDir)

                onlyIf {
                    val files = wwwDir.listFiles()
                    files != null && files.any { it.name != "index.html" }
                }

                group = "ludens"
                description = "Synchronizes web assets into Compose resources."
            }

            val dependencies = listOf(
                "generateComposeFileResources",
                "copyNonXmlValueResourcesForCommonMain"
            )

            tasks.matching { it.name in dependencies }
                .configureEach {
                    dependsOn(syncTask)
                }

            tasks.withType(KotlinCompile::class.java).configureEach {
                dependsOn(syncTask)
            }
        }
    }
}
