package ludens.build.helpers

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ManifestFiles
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import java.io.File

/**
 * Root asset store directory used by build-logic tasks.
 *
 * This points to `project/assets` from the repository root, not the current module.
 */
val Project.assetsStoreDir: File
    get() = rootProject.projectDir.resolve("project/assets")

/**
 * Project root www directory.
 *
 * This points to `project/www` from the repository root.
 */
val Project.projectWwwDir: File
    get() = rootProject.projectDir.resolve("project/www")

/**
 * Compose resource directory for the current module.
 *
 * This points to `src/commonMain/composeResources` under the current project directory.
 */
val Project.composeResourcesDir: File
    get() = projectDir.resolve("src/commonMain/composeResources")

/**
 * `files` subdirectory inside the [composeResourcesDir].
 */
val Project.composeResourcesFilesDir: File
    get() = composeResourcesDir.resolve("files")

/**
 * Base generation directory for Ludens build outputs.
 */
val Project.generationDir: File
    get() = layout.buildDirectory.dir("generated/ludens").get().asFile

/**
 * Compose-specific generation directory under [generationDir].
 */
val Project.composeGenerationDir: File
    get() = generationDir.resolve("compose")

/**
 * Android source directory for the current module.
 */
val Project.androidMainDir: File
    get() = projectDir.resolve("src/androidMain")

/**
 * Android manifest file path for the current module.
 */
val Project.androidManifestFile: File
    get() = androidMainDir.resolve("AndroidManifest.xml")

/**
 * Android-specific generation directory under [generationDir].
 */
val Project.androidGenerationDir: File
    get() = generationDir.resolve("android")

/**
 * iOS-specific generation directory under [generationDir].
 */
val Project.iosGenerationDir: File
    get() = generationDir.resolve("ios")

/**
 * Kotlin Multiplatform source sets for the current project, if the KMP plugin is applied.
 */
val Project.kmpSources: NamedDomainObjectContainer<KotlinSourceSet>?
    get() = extensions.findByType(KotlinMultiplatformExtension::class.java)?.sourceSets

/**
 * Shared `commonMain` source set, if Kotlin Multiplatform is present.
 */
val Project.composeKotlinSourceSet: KotlinSourceSet?
    get() = kmpSources?.findByName(
        "commonMain"
    )

/**
 * Android `androidMain` source set, if Kotlin Multiplatform is present.
 */
val Project.androidKotlinSourceSet: KotlinSourceSet?
    get() = kmpSources?.findByName("androidMain")

/**
 * Android Components extension when the Android plugin is applied.
 */
val Project.androidComponents: AndroidComponentsExtension<*, *, *>?
    get() = extensions.findByType(AndroidComponentsExtension::class.java)

/**
 * Executes [action] against the manifests collection for each Android variant.
 *
 * This is a small convenience wrapper around `androidComponents?.onVariants { ... }`.
 */
fun Project.onAndroidManifests(action: (manifests: ManifestFiles) -> Unit) {
    androidComponents?.onVariants {
        action(it.sources.manifests)
    }
}
