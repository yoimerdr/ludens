package ludens.build.compose.resources.icons

import ludens.build.helpers.deleteIfExists
import org.gradle.api.logging.Logger
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.file.Path
import javax.imageio.ImageIO

/**
 * Enumeration of Android launcher icon resource names used in mipmap and drawable
 * directories.
 *
 * @property iconName The file name (without extension) used for the icon resource.
 */
enum class AndroidIconLauncher(val iconName: String) {
    /** Standard launcher icon (legacy). */
    Launcher("ic_launcher"),

    /** Round launcher icon variant (legacy). */
    LauncherRound("ic_launcher_round"),

    /** Foreground layer of an adaptive icon. */
    LauncherForeground("ic_launcher_foreground"),

    /** Background layer of an adaptive icon. */
    LauncherBackground("ic_launcher_background"),
}

/**
 * Output format for generated Android raster icon assets.
 */
enum class AndroidIconFormat {
    /** Portable Network Graphics. */
    Png,

    /** WebP (requires JVM ImageIO WebP plugin). */
    Webp,
}

/**
 * Generates Android launcher icon assets (legacy + adaptive) from source images,
 * including VectorDrawable conversion, XML metadata files, and cleanup of stale
 * assets.
 *
 * @property logger Gradle logger used for lifecycle and warning output.
 */
class AndroidIconGenerator(private val logger: Logger) {

    /**
     * Resolution-to-pixel-size mapping for legacy (pre-adaptive) launcher icons.
     * Each entry corresponds to a mipmap resource directory.
     */
    private val legacyMipmapResolutions = mapOf(
        "mipmap-mdpi" to 48,
        "mipmap-hdpi" to 72,
        "mipmap-xhdpi" to 96,
        "mipmap-xxhdpi" to 144,
        "mipmap-xxxhdpi" to 192,
    )

    /**
     * Resolution-to-pixel-size mapping for adaptive-icon layers (foreground and
     * background). These sizes are 108 dp × 108 dp at each density bucket.
     */
    private val adaptiveMipmapResolutions = mapOf(
        "mipmap-mdpi" to 108,
        "mipmap-hdpi" to 162,
        "mipmap-xhdpi" to 216,
        "mipmap-xxhdpi" to 324,
        "mipmap-xxxhdpi" to 432,
    )

    /**
     * Attempts to convert an SVG file to an Android VectorDrawable XML using
     * AGP's `Svg2Vector` utility (accessed via reflection for cross-version
     * compatibility). The result is post-processed to enforce the standard
     * 108 dp viewport and apply the configured [iconScale] transform.
     *
     * @param svgFile The source SVG file.
     * @param outputFile Destination XML file (e.g. `drawable/ic_launcher_foreground.xml`).
     * @param iconScale Scale factor to apply via a centred `<group>` wrapper
     *   inside the VectorDrawable.
     * @return `true` when the conversion succeeds, `false` when it fails (caller
     *   should fall back to raster mipmaps).
     */
    fun tryConvertSvgToVectorDrawable(svgFile: File, outputFile: File, iconScale: Double): Boolean {
        return try {
            val tempFile = File.createTempFile("svg2vd_", ".xml")
            tempFile.deleteOnExit()

            FileOutputStream(tempFile).use { outStream ->
                val clazz = Class.forName("com.android.ide.common.vectordrawable.Svg2Vector")

                try {
                    val parseMethod =
                        clazz.getMethod("parseSvgToXml", Path::class.java, OutputStream::class.java)
                    parseMethod.invoke(null, svgFile.toPath(), outStream)
                } catch (e: NoSuchMethodException) {
                    val parseMethod =
                        clazz.getMethod("parseSvgToXml", File::class.java, OutputStream::class.java)
                    parseMethod.invoke(null, svgFile, outStream)
                }
            }

            val rawXml = tempFile.readText()
            tempFile.delete()

            val vpWidthRegex = """android:viewportWidth="([^"]+)"""".toRegex()
            val vpHeightRegex = """android:viewportHeight="([^"]+)"""".toRegex()

            val vpWidthMatch = vpWidthRegex.find(rawXml)
            val vpHeightMatch = vpHeightRegex.find(rawXml)

            val vpWidth = vpWidthMatch?.groupValues?.get(1)?.toDoubleOrNull() ?: 108.0
            val vpHeight = vpHeightMatch?.groupValues?.get(1)?.toDoubleOrNull() ?: 108.0

            val pivotX = vpWidth / 2.0
            val pivotY = vpHeight / 2.0

            var modifiedXml =
                rawXml.replace("""android:width="[^"]+"""".toRegex(), """android:width="108dp"""")
                    .replace("""android:height="[^"]+"""".toRegex(), """android:height="108dp"""")

            val vectorTagOpenIndex = modifiedXml.indexOf("<vector")
            val vectorTagCloseBracketIndex = modifiedXml.indexOf(">", vectorTagOpenIndex)
            val endVectorTagIndex = modifiedXml.lastIndexOf("</vector>")

            if (vectorTagOpenIndex != -1 && vectorTagCloseBracketIndex != -1 && endVectorTagIndex != -1) {
                val header = modifiedXml.take(vectorTagCloseBracketIndex + 1)
                val content =
                    modifiedXml.substring(vectorTagCloseBracketIndex + 1, endVectorTagIndex)
                val footer = modifiedXml.substring(endVectorTagIndex)

                val groupOpen = """
    <group
        android:scaleX="$iconScale"
        android:scaleY="$iconScale"
        android:pivotX="$pivotX"
        android:pivotY="$pivotY">
""".trimStart()
                val groupClose = "\n    </group>\n"

                modifiedXml = header + groupOpen + content + groupClose + footer
            }

            outputFile.parentFile.mkdirs()
            outputFile.writeText(modifiedXml)
            logger.lifecycle(
                "Successfully converted and scaled SVG ${svgFile.name} to VectorDrawable XML: ${outputFile.name}"
            )
            true
        } catch (e: Throwable) {
            logger.warn(
                "Svg2Vector conversion failed or not available for ${svgFile.name}: ${e.message}. " + "Falling back to rasterised mipmaps."
            )
            false
        }
    }

    /**
     * Generates the `mipmap-anydpi-v26/ic_launcher.xml` and
     * `mipmap-anydpi-v26/ic_launcher_round.xml` adaptive-icon definition files
     * that reference the foreground and background drawable layers.
     *
     * @param androidResDir Path to the Android `res/` directory.
     * @param backgroundPathOrColor Either a hex colour string (e.g. `"#FFFFFF"`)
     *   or a `@drawable` / `@mipmap` resource reference.
     * @param foregroundIsVectorDrawable When `true` the foreground is referenced
     *   as `@drawable/ic_launcher_foreground`; otherwise `@mipmap/...`.
     */
    fun generateAdaptiveIconXml(
        androidResDir: String,
        backgroundPathOrColor: String,
        foregroundIsVectorDrawable: Boolean,
    ) {
        val adaptiveIconXmlDir = File("$androidResDir/mipmap-anydpi-v26")
        if (!adaptiveIconXmlDir.exists()) adaptiveIconXmlDir.mkdirs()
        val backgroundDrawable: String =
            if (backgroundPathOrColor.startsWith("#")) "@color/ic_launcher_background"
            else if (backgroundPathOrColor.startsWith("@drawable")) "@drawable/ic_launcher_background"
            else "@mipmap/ic_launcher_background"
        val foregroundDrawable =
            if (foregroundIsVectorDrawable) "@drawable/ic_launcher_foreground" else "@mipmap/ic_launcher_foreground"
        val xmlContent = """
            <?xml version="1.0" encoding="utf-8"?>
            <adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
                <background android:drawable="$backgroundDrawable"/>
                <foreground android:drawable="$foregroundDrawable"/>
            </adaptive-icon>
        """.trimIndent()
        File(adaptiveIconXmlDir, "ic_launcher.xml").writeText(xmlContent)
        File(adaptiveIconXmlDir, "ic_launcher_round.xml").writeText(xmlContent)
    }

    /**
     * Creates a colour resource XML in `res/values/ic_launcher_background.xml`
     * when the background is a solid hex colour.
     *
     * @param androidResDir Path to the Android `res/` directory.
     * @param backgroundColor The hex colour string (e.g. `"#FF0000"`). No file is
     *   written when this does not start with `#`.
     */
    fun generateColorXmlIfNeeded(androidResDir: String, backgroundColor: String) {
        if (!backgroundColor.startsWith("#")) return
        val valuesDir = File("$androidResDir/values")
        if (!valuesDir.exists()) valuesDir.mkdirs()
        val colorsFile = File(valuesDir, "ic_launcher_background.xml")
        val xmlContent = """
            <?xml version="1.0" encoding="utf-8"?>
            <resources>
                <color name="ic_launcher_background">$backgroundColor</color>
            </resources>
        """.trimIndent()
        colorsFile.writeText(xmlContent)
    }

    /**
     * Deletes existing icon images (PNG and WebP) from the given mipmap
     * directories so stale assets from a previous generation do not remain.
     *
     * @param resolutions Set of mipmap folder names (e.g. `"mipmap-hdpi"`).
     * @param androidDir Path to the Android `res/` directory.
     * @param icons Icon entries whose files should be removed.
     */
    fun removeExistingAndroidIconImages(
        resolutions: Set<String>,
        androidDir: String,
        icons: Array<AndroidIconLauncher>,
    ) {
        resolutions.forEach { name ->
            val dir = File("$androidDir/$name")
            if (dir.exists()) {
                icons.forEach {
                    dir.resolve("${it.iconName}.png").deleteIfExists()
                    dir.resolve("${it.iconName}.webp").deleteIfExists()
                }
            }
        }
    }

    /**
     * Generates adaptive-icon layer images (foreground or background) as
     * standalone PNG/WebP rasters in the mipmap directories.
     *
     * Each image is rendered from [inputImageFile] at the pixel size dictated
     * by the density bucket.
     *
     * @param resolutions Resolution-to-pixel-size map.
     * @param androidDir Path to the Android `res/` directory.
     * @param icons Icon entries to generate (typically one per call).
     * @param inputImageFile Source image (SVG or raster).
     * @param format Desired output format (PNG or WebP).
     */
    fun generateAdaptiveAndroidIconImages(
        resolutions: Map<String, Int>,
        androidDir: String,
        icons: Array<AndroidIconLauncher>,
        inputImageFile: File,
        format: AndroidIconFormat,
    ) {
        val extension = when (format) {
            AndroidIconFormat.Png -> "png"
            AndroidIconFormat.Webp -> "webp"
        }
        resolutions.forEach { (folder, size) ->
            val dir = File("$androidDir/$folder")
            if (!dir.exists()) dir.mkdirs()
            icons.forEach { icon ->
                resizeAndSaveImage(
                    inputFile = inputImageFile,
                    width = size,
                    height = size,
                    outputFile = dir.resolve("${icon.iconName}.$extension"),
                    isRounded = false,
                    format = extension,
                )
            }
        }
    }

    /**
     * Generates legacy (pre-adaptive) launcher icons by compositing the
     * foreground over the background and writing the combined image to each
     * mipmap density bucket.
     *
     * For [AndroidIconLauncher.LauncherRound] a circular clip mask is applied.
     *
     * @param resolutions Resolution-to-pixel-size map.
     * @param androidDir Path to the Android `res/` directory.
     * @param icons Icon entries to generate (e.g. `Launcher` and `LauncherRound`).
     * @param foregroundImageFile Source image for the foreground layer.
     * @param backgroundImageFile Optional source image for the background layer.
     * @param backgroundColor Optional hex background colour (takes precedence over
     *   [backgroundImageFile] when non-null and starting with `#`).
     * @param iconScale Foreground scale factor relative to the icon bounding box
     *   (e.g. 0.75 for 75 %).
     * @param format Desired output format (PNG or WebP).
     */
    fun generateLegacyAndroidIconImages(
        resolutions: Map<String, Int>,
        androidDir: String,
        icons: Array<AndroidIconLauncher>,
        foregroundImageFile: File,
        backgroundImageFile: File?,
        backgroundColor: String?,
        iconScale: Double,
        format: AndroidIconFormat,
    ) {
        val extension = when (format) {
            AndroidIconFormat.Png -> "png"
            AndroidIconFormat.Webp -> "webp"
        }
        resolutions.forEach { (folder, size) ->
            val dir = File("$androidDir/$folder")
            if (!dir.exists()) dir.mkdirs()
            icons.forEach { icon ->
                val combinedImg = createCombinedImage(
                    foregroundFile = foregroundImageFile,
                    backgroundFile = backgroundImageFile,
                    backgroundColor = backgroundColor,
                    width = size,
                    height = size,
                    iconScale = iconScale,
                    isRounded = icon == AndroidIconLauncher.LauncherRound,
                )
                val success = ImageIO.write(
                    combinedImg, extension, dir.resolve("${icon.iconName}.$extension")
                )
                if (!success) {
                    val writers = ImageIO.getWriterFormatNames().joinToString(", ")
                    logger.error(
                        "Failed to write combined image in format '$extension' to ${
                            dir.resolve("${icon.iconName}.$extension").absolutePath
                        }. Available formats in ImageIO: $writers"
                    )
                }
            }
        }
    }

    /**
     * Removes all previous Android raster icon assets in both legacy and
     * adaptive mipmap directories, including VectorDrawable XML files and
     * stale colour / adaptive-icon definition files.
     *
     * Call this before generating fresh icons to guarantee no orphaned assets
     * remain from a previous configuration.
     *
     * @param androidResDir Path to the Android `res/` directory.
     */
    fun cleanStaleAssets(androidResDir: String) {
        removeExistingAndroidIconImages(
            legacyMipmapResolutions.keys, androidResDir,
            arrayOf(
                AndroidIconLauncher.Launcher,
                AndroidIconLauncher.LauncherRound,
                AndroidIconLauncher.LauncherForeground
            ),
        )
        removeExistingAndroidIconImages(
            adaptiveMipmapResolutions.keys, androidResDir,
            arrayOf(AndroidIconLauncher.LauncherForeground, AndroidIconLauncher.LauncherBackground),
        )
        File("$androidResDir/drawable-v24").apply {
            if (exists()) deleteRecursively()
        }
        File("$androidResDir/mipmap-anydpi-v26").apply {
            if (exists()) {
                resolve("ic_launcher.xml").deleteIfExists()
                resolve("ic_launcher_round.xml").deleteIfExists()
            }
        }
        File("$androidResDir/values").apply {
            if (exists()) resolve("ic_launcher_background.xml").deleteIfExists()
        }
    }

    /**
     * Returns the appropriate resource reference for the Android background
     * layer based on whether it is a solid colour, a VectorDrawable, or a
     * raster mipmap.
     *
     * @param backgroundIsColor When `true` the background is a hex colour string.
     * @param backgroundIsVectorDrawable When `true` the background was converted
     *   to a VectorDrawable XML in `drawable/`.
     * @param srcIconBackgroundName The raw background value from configuration
     *   (either a hex colour or a source file name).
     * @return A resource reference string (`"#RRGGBB"`, `"@drawable/..."`, or
     *   `"@mipmap/..."`).
     */
    fun resolveBackgroundReference(
        backgroundIsColor: Boolean,
        backgroundIsVectorDrawable: Boolean,
        srcIconBackgroundName: String,
    ): String = when {
        backgroundIsColor -> srcIconBackgroundName
        backgroundIsVectorDrawable -> "@drawable/ic_launcher_background"
        else -> "@mipmap/ic_launcher_background"
    }

    /** @see [legacyMipmapResolutions] */
    fun legacyResolutions(): Map<String, Int> = legacyMipmapResolutions

    /** @see [adaptiveMipmapResolutions] */
    fun adaptiveResolutions(): Map<String, Int> = adaptiveMipmapResolutions
}
