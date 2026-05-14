/*
 * Created by: https://github.com/rainbowtrash2333.
 *
 * Edited by: https://github.com/yoimerdr.
 */

package ludens.build.compose.language

import ludens.build.compose.configuration.ludensConfiguration
import ludens.build.helpers.assetsStoreDir
import ludens.build.helpers.composeGenerationDir
import ludens.build.helpers.composeKotlinSourceSet
import ludens.build.helpers.composeResourcesDir
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Registers the code generation task that produces [LanguageMetadata] from
 * the Compose resource value directories.
 *
 * The plugin scans the compose resource tree, discovers language tags and
 * generates a Kotlin object that the application uses to enumerate supported
 * languages and resolve display labels at runtime.
 *
 * ### Convention
 * Adding a new language requires only **one** file:
 * - `composeResources/values-{tag}/strings.xml` — with a `<string name="{resourceName}">`
 *   entry for the language's self-name, where `{resourceName}` is the normalized
 *   resource key derived from `{tag}` (for example, `pt-BR` uses
 *   `<string name="pt_br">...</string>`).
 *
 * The rest is discovered automatically at build time.
 */
class LanguageMetadataPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val resolvedBaseLanguageTag = providers.provider {
                resolveBaseLanguageTag(
                    assetsStoreDir.resolve("languages"),
                    ludensConfiguration.languages,
                ) ?: "en"
            }

            val generateTask =
                tasks.register<LanguageMetadataGenerationTask>("generateLanguageMetadata") {
                    packageName.set("com.yoimerdr.compose.ludens.generated.language")
                    objectName.set("LanguageMetadata")
                    baseLanguageTag.set(resolvedBaseLanguageTag)

                    resourceDir.set(
                        composeResourcesDir,
                    )

                    outputFile.set(
                        composeGenerationDir
                            .resolve("language")
                            .resolve("LanguageMetadata.kt")
                    )
                }

            afterEvaluate {
                composeKotlinSourceSet?.kotlin
                    ?.srcDir(generateTask.map { it.outputFile.get().asFile.parentFile })
            }

            generateTask.configure {
                dependsOn("ludensLanguageStringsSync")
            }

            tasks.withType(KotlinCompile::class.java)
                .configureEach {
                    dependsOn(generateTask)
                }
        }
    }
}
