package ludens.build.compose.language

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
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

            val generateTask =
                tasks.register<LanguageMetadataGenerationTask>("generateLanguageMetadata") {
                    packageName.set("com.yoimerdr.compose.ludens.generated.language")
                    objectName.set("LanguageMetadata")

                    resourceDir.set(
                        layout.projectDirectory.dir("src/commonMain/composeResources")
                    )

                    outputFile.set(
                        layout.buildDirectory.file(
                            "generated/ludens/compose/language/LanguageMetadata.kt"
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
