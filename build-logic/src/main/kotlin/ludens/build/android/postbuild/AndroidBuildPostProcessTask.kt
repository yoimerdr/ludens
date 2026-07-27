package ludens.build.android.postbuild

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.IgnoreEmptyDirectories
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Incremental, cacheable Gradle task that renames and copies/moves compiled Android APKs and AABs.
 *
 * Inputs are passed lazily via Gradle properties without direct [org.gradle.api.Project] access inside
 * [processArtifacts] to support configuration cache and build caching.
 *
 * Usage:
 * ```kotlin
 * tasks.register("ludensPostBuildRelease", AndroidBuildProcessTask::class.java) {
 *     inputApkDir.set(layout.buildDirectory.dir("outputs/apk/release"))
 *     inputBundleDir.set(layout.buildDirectory.dir("outputs/bundle/release"))
 *     targetOutputDir.set(rootProject.layout.projectDirectory.dir("output/builds"))
 *     variantName.set("release")
 *     appName.set("Ludens")
 *     versionName.set("0.4.0")
 *     versionCode.set(1)
 *     minSdk.set(21)
 *     targetSdk.set(36)
 *     appId.set("com.yoimerdr.compose.ludens")
 *     pattern.set("{appName}-{versionName}-{buildType}")
 *     actionMode.set("copy")
 * }
 * ```
 */
@CacheableTask
abstract class AndroidBuildPostProcessTask : DefaultTask() {

    /**
     * Requested Gradle task names from start parameters.
     */
    @get:Input
    @get:Optional
    abstract val requestedTasks: ListProperty<String>

    /**
     * Input directory containing compiled APK artifacts.
     */
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:Optional
    @get:IgnoreEmptyDirectories
    abstract val inputApkDir: DirectoryProperty

    /**
     * Input directory containing compiled Android App Bundle (AAB) artifacts.
     */
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:Optional
    @get:IgnoreEmptyDirectories
    abstract val inputBundleDir: DirectoryProperty

    /**
     * Fallback input directory at module level (e.g. `androidApp/release` created by Android Studio wizard).
     */
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:Optional
    @get:IgnoreEmptyDirectories
    abstract val moduleVariantDir: DirectoryProperty

    /**
     * Target output directory where processed artifacts are stored.
     */
    @get:OutputDirectory
    abstract val targetOutputDir: DirectoryProperty

    /**
     * Target build variant name (e.g. `"release"`, `"debug"`).
     */
    @get:Input
    abstract val variantName: Property<String>

    /**
     * Application display name.
     */
    @get:Input
    abstract val appName: Property<String>

    /**
     * Application version name string.
     */
    @get:Input
    abstract val versionName: Property<String>

    /**
     * Application version code integer.
     */
    @get:Input
    abstract val versionCode: Property<Int>

    /**
     * Minimum supported Android API level.
     */
    @get:Input
    abstract val minSdk: Property<Int>

    /**
     * Target Android API level.
     */
    @get:Input
    abstract val targetSdk: Property<Int>

    /**
     * Android Application ID package name.
     */
    @get:Input
    abstract val appId: Property<String>

    /**
     * Naming template pattern for output artifacts.
     */
    @get:Input
    abstract val pattern: Property<String>

    /**
     * Operation mode (`"copy"` or `"move"`).
     */
    @get:Input
    abstract val actionMode: Property<String>

    /**
     * Target artifact file extension to process (`"apk"` or `"aab"`).
     */
    @get:Input
    @get:Optional
    abstract val targetFileType: Property<String>

    /**
     * Process input artifacts and copy/move them to the target output directory using the formatted pattern name.
     */
    @Suppress("NewApi") // For suppress nio utilities
    @TaskAction
    fun processArtifacts() {
        val targetExt = targetFileType.orNull?.lowercase()

        val tasks = requestedTasks.orNull ?: emptyList()
        if (tasks.isNotEmpty()) {
            val isBundleRequested = tasks.any { it.contains("bundle", ignoreCase = true) }
            val isApkRequested = tasks.any {
                it.contains("assemble", ignoreCase = true) ||
                    (it.contains("package", ignoreCase = true) && !it.contains("bundle", ignoreCase = true)) ||
                    it.contains("apk", ignoreCase = true)
            }

            if (targetExt == "aab" && !isBundleRequested && isApkRequested) {
                logger.info("[ludens:post-build] Skipped AAB post-processing for variant '${variantName.orNull}': only APK build requested.")
                return
            }
            if (targetExt == "apk" && !isApkRequested && isBundleRequested) {
                logger.info("[ludens:post-build] Skipped APK post-processing for variant '${variantName.orNull}': only Bundle build requested.")
                return
            }
        }

        val targetDirFile = targetOutputDir.get().asFile
        if (!targetDirFile.exists()) {
            targetDirFile.mkdirs()
        }

        val targetDirCanonicalPath = targetDirFile.canonicalPath

        fun scanDir(dir: File?): List<File> {
            if (dir == null || !dir.exists() || !dir.isDirectory) return emptyList()
            return dir.walkTopDown()
                .filter { file ->
                    file.isFile &&
                        (targetExt == null || file.extension.equals(targetExt, ignoreCase = true)) &&
                        !file.canonicalPath.startsWith(targetDirCanonicalPath)
                }
                .toList()
        }

        // Priority 1: Primary AGP build output directory
        val primaryDir = if (targetExt == "aab") inputBundleDir.orNull?.asFile else inputApkDir.orNull?.asFile
        var artifactFiles = scanDir(primaryDir)

        // Priority 2: Fallback directory (e.g. androidApp/release created by Android Studio wizard)
        if (artifactFiles.isEmpty()) {
            val fallbackDir = moduleVariantDir.orNull?.asFile
            if (fallbackDir != null && fallbackDir.canonicalPath != primaryDir?.canonicalPath) {
                artifactFiles = scanDir(fallbackDir)
            }
        }

        if (artifactFiles.isEmpty()) {
            logger.warn(
                "[ludens:post-build] No matching ${targetExt?.uppercase() ?: "artifact"} files found for variant '${variantName.orNull}'."
            )
            return
        }

        val timestamp = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
        // Sanitize placeholder values to remove invalid OS characters (e.g. "Ludens: Special" -> "Ludens_ Special")
        val rawNameVal = appName.get()
        val nameVal = sanitizeFileNamePart(rawNameVal)
        val rawVersionVal = versionName.get()
        val versionVal = sanitizeFileNamePart(rawVersionVal)
        val codeVal = versionCode.get().toString()
        val variantVal = sanitizeFileNamePart(variantName.get())
        val minSdkVal = minSdk.get().toString()
        val targetSdkVal = targetSdk.get().toString()
        val appIdVal = sanitizeFileNamePart(appId.get())
        val patternTemplate = pattern.get()
        val isMove = actionMode.get().equals("move", ignoreCase = true)

        val isSingleArtifact = artifactFiles.size == 1

        artifactFiles.forEachIndexed { index, file ->
            val ext = file.extension
            // Substitute pattern placeholders with sanitized metadata (e.g. "{appName}-{version}" -> "Ludens-1.0.apk")
            val formattedName = patternTemplate
                .replace("{appName}", nameVal)
                .replace("{name}", nameVal)
                .replace("{versionName}", versionVal)
                .replace("{version}", versionVal)
                .replace("{versionCode}", codeVal)
                .replace("{code}", codeVal)
                .replace("{buildType}", variantVal)
                .replace("{variant}", variantVal)
                .replace("{minSdk}", minSdkVal)
                .replace("{targetSdk}", targetSdkVal)
                .replace("{timestamp}", timestamp)
                .replace("{date}", timestamp)
                .replace("{appId}", appIdVal)
                .replace("{package}", appIdVal)
                .replace("{artifactType}", ext)
                .replace("{type}", ext)

            val baseFileName = if (formattedName.endsWith(".$ext", ignoreCase = true)) {
                formattedName
            } else {
                "$formattedName.$ext"
            }

            val finalFileName = if (isSingleArtifact) {
                baseFileName
            } else {
                val nameWithoutExt = baseFileName.removeSuffix(".$ext")
                val discriminator = file.nameWithoutExtension
                    .removePrefix("app-")
                    .removePrefix("composeApp-")
                    .removePrefix("androidApp-")
                if (discriminator.isNotBlank() && !nameWithoutExt.contains(discriminator)) {
                    "$nameWithoutExt-$discriminator.$ext"
                } else if (index > 0) {
                    "$nameWithoutExt-$index.$ext"
                } else {
                    baseFileName
                }
            }

            val destFile = File(targetDirFile, finalFileName)
            // Ensure parent subdirectories exist if pattern includes nested paths (e.g. "{buildType}/{appName}-{version}")
            destFile.parentFile?.let { parent ->
                if (!parent.exists()) parent.mkdirs()
            }

            // Perform move via Java NIO for cross-drive safety, or fallback to copy & delete
            if (isMove) {
                logger.lifecycle("[ludens:post-build] Moving artifact '${file.name}' to '${destFile.absolutePath}'")
                try {
                    Files.move(
                        file.toPath(),
                        destFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                } catch (e: Exception) {
                    logger.info("[ludens:post-build] Direct NIO move failed for '${file.name}' (${e.message}), falling back to copy & delete.")
                    file.copyTo(destFile, overwrite = true)
                    file.delete()
                }
            } else {
                logger.lifecycle("[ludens:post-build] Copying artifact '${file.name}' to '${destFile.absolutePath}'")
                file.copyTo(destFile, overwrite = true)
            }
        }
    }

    // Strips invalid OS filename characters (: \ / * ? " < > |)
    private fun sanitizeFileNamePart(value: String): String {
        return value.replace(Regex("[:\\\\/*?\"<>|]"), "_").trim()
    }
}
