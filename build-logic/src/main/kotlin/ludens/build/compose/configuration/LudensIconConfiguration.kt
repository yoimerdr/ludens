package ludens.build.compose.configuration

import ludens.build.compose.resources.icons.AndroidIconFormat
import java.io.Serializable

/**
 * App icon generator settings loaded from `ludens.properties`.
 *
 * Exposes paths, nested target objects, and formatting configuration for icon assets.
 */
data class LudensIconConfiguration(
    /**
     * Base icon file name (without extension) used as the fallback when dedicated foreground
     * or background files are not provided.
     *
     * Maps from `ludens.icons.name`.
     */
    val name: String = "icon",
    /**
     * Foreground layer file name (without extension) composited over the background layer.
     *
     * Maps from `ludens.icons.foreground`.
     */
    val foreground: String = "icon_foreground",
    /**
     * Background layer file name (without extension) or a hex colour string prefixed with `#`.
     *
     * When it starts with `#`, it is treated as a solid colour; otherwise it must correspond
     * to a valid image file in the icon assets directory.
     *
     * Maps from `ludens.icons.background`.
     */
    val background: String = "icon_background",
    /**
     * Android-specific icon configuration.
     *
     * Maps from `ludens.icons.android.*`.
     */
    val android: LudensIconAndroidConfiguration = LudensIconAndroidConfiguration(),
    /**
     * iOS-specific icon configuration.
     *
     * Maps from `ludens.icons.ios.*`.
     */
    val ios: LudensIconIosConfiguration = LudensIconIosConfiguration(),
    /**
     * Foreground scale factor inside the icon bounding-box (0.0–1.0).
     *
     * Maps from `ludens.icons.scale`.
     */
    val scale: Double = 0.75,
) : Serializable

/**
 * Android-specific icon configuration loaded from `ludens.properties`.
 *
 * Controls whether Android icons are generated, the output raster format, and Play Store
 * listing icon generation.
 */
data class LudensIconAndroidConfiguration(
    /**
     * Whether to generate Android icon assets.
     *
     * Maps from `ludens.icons.android.enable`.
     */
    val enable: Boolean = true,
    /**
     * Raster format for Android icon output.
     *
     * Supported values: `png`, `webp`. Falls back to PNG when WebP is not supported by the
     * runtime ImageIO.
     *
     * Maps from `ludens.icons.android.format`.
     */
    val format: String = "png",
    /**
     * Whether to generate a 512×512 Play Store listing icon.
     *
     * Maps from `ludens.icons.android.playstore`.
     */
    val playstore: Boolean = true,
) : Serializable {
    /**
     * Resolves the case-insensitive format string to the [AndroidIconFormat] enum.
     */
    val resolvedFormat: AndroidIconFormat
        get() = when (format.lowercase().trim()) {
            "webp" -> AndroidIconFormat.Webp
            else -> AndroidIconFormat.Png
        }
}

/**
 * iOS-specific icon configuration loaded from `ludens.properties`.
 *
 * Controls whether iOS app icon assets are generated into the Xcode asset catalogue.
 */
data class LudensIconIosConfiguration(
    /**
     * Whether to generate iOS app icon assets.
     *
     * Maps from `ludens.icons.ios.enable`.
     */
    val enable: Boolean = true,
) : Serializable
