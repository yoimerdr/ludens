package ludens.build.compose.resources

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class ComposeResourceFilesPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            val generateTask =
                tasks.register<ComposeResourceFilesTask>("generateComposeFileResources") {
                    inputDir.set(
                        layout.projectDirectory.dir("src/commonMain/composeResources/files")
                    )
                    outputFile.set(
                        layout.buildDirectory.file(
                            "generated/fileRes/commonMain/kotlin/com/yoimerdr/compose/ludens/res/FileRes.kt"
                        )
                    )
                    packageName.set("com.yoimerdr.compose.ludens.generated.res")
                    includePatterns.set(listOf(
                        "**/www/index.html", "**/boot/**",
                        "**/fallback/**",
                    ))
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