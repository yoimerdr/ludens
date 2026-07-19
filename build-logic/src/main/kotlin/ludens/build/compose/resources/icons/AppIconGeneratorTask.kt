/*
 * Copyright 2024 Qamar A. Safadi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * NOTE: This file is a localized port and adaptation of KMPAppIconGeneratorPlugin,
 * integrated into the Ludens project's custom build-logic.
 */

package ludens.build.compose.resources.icons

import ludens.build.helpers.deleteIfExists
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.imageio.ImageIO

/**
 * Gradle task that generates Android and iOS app icons from a single source
 * image (SVG or PNG).
 *
 * ### What it produces
 *
 * **Android:**
 * - Legacy launcher icons (`ic_launcher`, `ic_launcher_round`) at 5 mipmap
 *   densities, with foreground composited over background.
 * - Adaptive-icon layers (`ic_launcher_foreground`, `ic_launcher_background`)
 *   as raster images _or_ VectorDrawable XMLs when the source is SVG and
 *   AGP's `Svg2Vector` is available on the classpath.
 * - Adaptive-icon XML definitions in `mipmap-anydpi-v26`.
 * - Play Store listing icon (512 × 512 PNG).
 *
 * **iOS:**
 * - 18 PNG files covering iPhone, iPad, and App Store marketing at all
 *   required scales, composited with the same foreground/background
 *   treatment.
 * - `Contents.json` manifest for the `AppIcon.appiconset` asset catalogue.
 *
 * @property sourceIconName Base icon file name (without extension).
 * @property sourceIconForegroundName Foreground layer file name (without
 *   extension); falls back to [sourceIconName].
 * @property sourceIconBackgroundName Background layer file name (without
 *   extension) _or_ a hex colour string prefixed with `#`.
 * @property outputAndroid Whether to generate Android icon assets.
 * @property outputIos Whether to generate iOS icon assets.
 * @property outputPlaystore Whether to generate the Play Store listing icon.
 * @property androidIconFormat Raster format for Android icons (PNG or WebP).
 * @property iconScale Foreground scale factor inside the icon bounding-box
 *   (default 0.75).
 * @property commonResourcesDir Directory containing the source icon files.
 * @property rootDir Project root directory.
 */
abstract class AppIconGeneratorTask : DefaultTask() {

    @get:Input
    abstract val sourceIconName: Property<String>

    @get:Input
    abstract val sourceIconForegroundName: Property<String>

    @get:Input
    abstract val sourceIconBackgroundName: Property<String>

    @get:Input
    abstract val outputAndroid: Property<Boolean>

    @get:Input
    abstract val outputIos: Property<Boolean>

    @get:Input
    abstract val outputPlaystore: Property<Boolean>

    @get:Input
    abstract val androidIconFormat: Property<AndroidIconFormat>

    @get:Input
    abstract val iconScale: Property<Double>

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val commonResourcesDir: DirectoryProperty

    @get:Internal
    abstract val androidResDir: DirectoryProperty

    @get:OutputDirectory
    @get:Optional
    abstract val iosAppIconSetDir: DirectoryProperty

    @get:OutputFile
    @get:Optional
    abstract val playstoreIconFile: RegularFileProperty

    @get:Internal
    abstract val rootDir: Property<File>

    @TaskAction
    fun generate() {
        val root = rootDir.get()
        val commonResourcesPath = commonResourcesDir.get().asFile
        val androidResDirFile = androidResDir.orNull?.asFile ?: File("${root}/composeApp/src/androidMain/res")
        val androidResDir = androidResDirFile.absolutePath
        val iosAppIconSetDirFile = iosAppIconSetDir.orNull?.asFile ?: File("${root}/iosApp/iosApp/Assets.xcassets/AppIcon.appiconset")

        val srcIconName = sourceIconName.get()
        val srcIconForegroundName = sourceIconForegroundName.get()
        val srcIconBackgroundName = sourceIconBackgroundName.get()
        val scale = iconScale.getOrElse(0.75)

        val foregroundSourceFile = sequenceOf(
            File("$commonResourcesPath/$srcIconForegroundName.svg"),
            File("$commonResourcesPath/$srcIconForegroundName.png"),
            File("$commonResourcesPath/$srcIconName.svg"),
            File("$commonResourcesPath/$srcIconName.png"),
        ).firstOrNull { it.exists() }
            ?: error(
                "No source foreground image file found at $commonResourcesPath " +
                    "using configured names ('$srcIconForegroundName' or '$srcIconName')"
            )

        val backgroundIsColor = srcIconBackgroundName.startsWith("#")

        val backgroundSourceFile = if (backgroundIsColor) null else {
            val foundFile = sequenceOf(
                File("$commonResourcesPath/$srcIconBackgroundName.svg"),
                File("$commonResourcesPath/$srcIconBackgroundName.png"),
            ).firstOrNull { it.exists() }
            
            if (foundFile == null && srcIconBackgroundName.isNotEmpty()) {
                error(
                    "Background resource '$srcIconBackgroundName' configured, but no corresponding " +
                        "SVG or PNG file was found in $commonResourcesPath. " +
                        "Please provide a valid background file or use a hex color string (e.g. '#FFFFFF')."
                )
            }
            foundFile
        }

        val androidGenerator = AndroidIconGenerator(logger)

        var foregroundIsVectorDrawable = false
        var backgroundIsVectorDrawable = false

        if (outputAndroid.get()) {
            if (foregroundSourceFile.extension == "svg") {
                val vdForegroundFile = File("$androidResDir/drawable/ic_launcher_foreground.xml")
                vdForegroundFile.deleteIfExists()
                foregroundIsVectorDrawable =
                    androidGenerator.tryConvertSvgToVectorDrawable(foregroundSourceFile, vdForegroundFile, scale)
            }
            if (backgroundSourceFile != null && backgroundSourceFile.extension == "svg") {
                val vdBackgroundFile = File("$androidResDir/drawable/ic_launcher_background.xml")
                vdBackgroundFile.deleteIfExists()
                backgroundIsVectorDrawable =
                    androidGenerator.tryConvertSvgToVectorDrawable(backgroundSourceFile, vdBackgroundFile, scale)
            }
        }

        val androidBackgroundPathOrColor = androidGenerator.resolveBackgroundReference(
            backgroundIsColor, backgroundIsVectorDrawable, srcIconBackgroundName,
        )

        if (outputAndroid.get()) {
            logger.lifecycle("Generating Android icons...")

            androidGenerator.cleanStaleAssets(androidResDir)

            var format = androidIconFormat.get()
            val writers = ImageIO.getWriterFormatNames().map { it.lowercase() }
            if (format == AndroidIconFormat.Webp && "webp" !in writers) {
                logger.warn(
                    "WARNING: WebP format is not supported by your JVM ImageIO " +
                        "(likely JNI loading failed). Automatically falling back to " +
                        "PNG format for compatibility."
                )
                format = AndroidIconFormat.Png
            }

            androidGenerator.generateLegacyAndroidIconImages(
                resolutions = androidGenerator.legacyResolutions(),
                androidDir = androidResDir,
                icons = arrayOf(AndroidIconLauncher.Launcher, AndroidIconLauncher.LauncherRound),
                foregroundImageFile = foregroundSourceFile,
                backgroundImageFile = backgroundSourceFile,
                backgroundColor = if (backgroundIsColor) androidBackgroundPathOrColor else null,
                iconScale = scale,
                format = format,
            )

            if (!foregroundIsVectorDrawable) {
                androidGenerator.generateAdaptiveAndroidIconImages(
                    resolutions = androidGenerator.adaptiveResolutions(),
                    androidDir = androidResDir,
                    icons = arrayOf(AndroidIconLauncher.LauncherForeground),
                    inputImageFile = foregroundSourceFile,
                    format = format,
                )
            }

            if (backgroundSourceFile != null && !backgroundIsVectorDrawable) {
                androidGenerator.generateAdaptiveAndroidIconImages(
                    resolutions = androidGenerator.adaptiveResolutions(),
                    androidDir = androidResDir,
                    icons = arrayOf(AndroidIconLauncher.LauncherBackground),
                    inputImageFile = backgroundSourceFile,
                    format = format,
                )
            }

            val vdForegroundFile = File("$androidResDir/drawable/ic_launcher_foreground.xml")
            if (!foregroundIsVectorDrawable) vdForegroundFile.deleteIfExists()

            val vdBackgroundFile = File("$androidResDir/drawable/ic_launcher_background.xml")
            if (backgroundSourceFile == null || !backgroundIsVectorDrawable) vdBackgroundFile.deleteIfExists()

            androidGenerator.generateAdaptiveIconXml(
                androidResDir, androidBackgroundPathOrColor, foregroundIsVectorDrawable,
            )

            if (backgroundIsColor) {
                androidGenerator.generateColorXmlIfNeeded(androidResDir, androidBackgroundPathOrColor)
            }

            if (outputPlaystore.get()) {
                logger.lifecycle("Generating Play Store icon...")
                val playstoreFile = playstoreIconFile.orNull?.asFile ?: File("${root}/composeApp/src/androidMain/ic_launcher-playstore.png")
                playstoreFile.deleteIfExists()
                val combinedImg = createCombinedImage(
                    foregroundFile = foregroundSourceFile,
                    backgroundFile = backgroundSourceFile,
                    backgroundColor = if (backgroundIsColor) androidBackgroundPathOrColor else null,
                    width = 512,
                    height = 512,
                    iconScale = scale,
                    isRounded = false,
                )
                ImageIO.write(combinedImg, "png", playstoreFile)
            }
        } else {
            logger.lifecycle("Skipping Android icon generation as per configuration.")
        }

        if (outputIos.get()) {
            logger.lifecycle("Generating iOS icons...")
            if (!iosAppIconSetDirFile.exists()) iosAppIconSetDirFile.mkdirs()

            iosAppIconSetDirFile.listFiles()?.forEach { file ->
                if (file.extension == "png" || file.name == "Contents.json") file.delete()
            }

            val iosGenerator = IosIconGenerator(logger)

            iosGenerator.iconSpecs.forEach { spec ->
                val combinedImg = createCombinedImage(
                    foregroundFile = foregroundSourceFile,
                    backgroundFile = backgroundSourceFile,
                    backgroundColor = if (backgroundIsColor) androidBackgroundPathOrColor else null,
                    width = spec.actualSize,
                    height = spec.actualSize,
                    iconScale = scale,
                    isRounded = false,
                )
                ImageIO.write(combinedImg, "png", File(iosAppIconSetDirFile, spec.filename))
            }
            iosGenerator.generateContentsJson(iosAppIconSetDirFile, iosGenerator.iconSpecs)
        } else {
            logger.lifecycle("Skipping iOS icon generation as per configuration.")
        }
    }
}
