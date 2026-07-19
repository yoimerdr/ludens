package ludens.build.compose.resources.icons

import com.kitfox.svg.SVGDiagram
import com.kitfox.svg.SVGUniverse
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.RenderingHints
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Renders an SVG file to a [BufferedImage] at the specified dimensions, preserving
 * aspect ratio and centering the result.
 *
 * @param svgFile The source SVG file.
 * @param targetWidth Desired output width in pixels.
 * @param targetHeight Desired output height in pixels.
 * @return A new [BufferedImage] with type [BufferedImage.TYPE_INT_ARGB].
 */
fun renderSvgToImage(
    svgFile: File,
    targetWidth: Int,
    targetHeight: Int,
): BufferedImage {
    val svgUniverse = SVGUniverse()
    val diagram: SVGDiagram = svgUniverse.getDiagram(svgFile.toURI())
    val originalWidth = diagram.width
    val originalHeight = diagram.height

    val bufferedImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB)
    val graphics = bufferedImage.createGraphics()
    graphics.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
    )
    graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
    graphics.setRenderingHint(
        RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE
    )

    val scaleFactor = minOf(targetWidth / originalWidth, targetHeight / originalHeight)
    val xOffset = (targetWidth - originalWidth * scaleFactor) / 2
    val yOffset = (targetHeight - originalHeight * scaleFactor) / 2
    val transform = AffineTransform()
    transform.translate(xOffset.toDouble(), yOffset.toDouble())
    transform.scale(scaleFactor.toDouble(), scaleFactor.toDouble())
    graphics.transform = transform
    diagram.render(graphics)
    graphics.dispose()

    return bufferedImage
}

/**
 * Reads an image from [inputFile] (SVG or raster), resizes it to the given dimensions,
 * optionally applies a circular mask, and writes it to [outputFile].
 *
 * @param inputFile Source image (SVG or PNG/JPEG raster).
 * @param width Target width in pixels.
 * @param height Target height in pixels.
 * @param outputFile Destination file to write.
 * @param isRounded When `true`, applies a circular (elliptical) clip mask.
 * @param format Output format name recognised by [ImageIO] (e.g. `"png"`, `"webp"`).
 */
fun resizeAndSaveImage(
    inputFile: File,
    width: Int,
    height: Int,
    outputFile: File,
    isRounded: Boolean = false,
    format: String = "png",
) {
    val resizedImage: BufferedImage = if (inputFile.extension == "svg") {
        renderSvgToImage(inputFile, width, height)
    } else {
        val originalImage: BufferedImage = ImageIO.read(inputFile)
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val graphics = img.createGraphics()
        graphics.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC
        )
        graphics.setRenderingHint(
            RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY
        )
        graphics.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        )
        graphics.drawImage(originalImage, 0, 0, width, height, null)
        graphics.dispose()
        img
    }

    if (isRounded) {
        val graphics = resizedImage.createGraphics()
        val mask = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val g2 = mask.createGraphics()
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.fill(Ellipse2D.Double(0.0, 0.0, width.toDouble(), height.toDouble()))
        g2.dispose()
        graphics.composite = AlphaComposite.DstIn
        graphics.drawImage(mask, 0, 0, null)
        graphics.dispose()
    }
    val success = ImageIO.write(resizedImage, format, outputFile)
    if (!success) {
        val writers = ImageIO.getWriterFormatNames().joinToString(", ")
        error("Failed to write image in format '$format' to ${outputFile.absolutePath}. Available formats in ImageIO: $writers")
    }
}

/**
 * Combines a foreground image over a background (color or image) and optionally applies
 * a circular mask.
 *
 * Composition order:
 * 1. Fill background with [backgroundColor] (if hex) or draw [backgroundFile] scaled to
 *    [width]x[height].
 * 2. Draw the foreground centred and scaled by [iconScale] (default 75 %) so it sits
 *    inside the "safe zone" of an adaptive icon.
 * 3. If [isRounded] is `true`, clip the result with an elliptical mask.
 *
 * When no background is provided the foreground is drawn at full scale with transparency
 * preserved.
 *
 * @param foregroundFile Source image for the foreground layer.
 * @param backgroundFile Optional source image for the background layer (ignored when
 *   [backgroundColor] is set).
 * @param backgroundColor Optional hex colour string (e.g. `"#FF0000"`). When non-null and
 *   starting with `#`, this colour is used instead of [backgroundFile].
 * @param width Output width in pixels.
 * @param height Output height in pixels.
 * @param iconScale Fraction of the total size the foreground occupies (centred).
 * @param isRounded When `true` applies a circular clip mask.
 * @return A composited [BufferedImage].
 */
fun createCombinedImage(
    foregroundFile: File,
    backgroundFile: File?,
    backgroundColor: String?,
    width: Int,
    height: Int,
    iconScale: Double,
    isRounded: Boolean = false,
): BufferedImage {
    val combined = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g = combined.createGraphics()
    g.setRenderingHint(
        RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC
    )
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

    val hasBackground =
        (backgroundColor != null && backgroundColor.startsWith("#")) || (backgroundFile != null && backgroundFile.exists())

    if (hasBackground) {
        if (backgroundColor != null && backgroundColor.startsWith("#")) {
            g.color = parseHexColor(backgroundColor)
            g.fillRect(0, 0, width, height)
        } else if (backgroundFile != null && backgroundFile.exists()) {
            if (backgroundFile.extension == "svg") {
                val bgImg = renderSvgToImage(backgroundFile, width, height)
                g.drawImage(bgImg, 0, 0, null)
            } else {
                val bgImg = ImageIO.read(backgroundFile)
                g.drawImage(bgImg, 0, 0, width, height, null)
            }
        }

        val fgSize = (width * iconScale).toInt()
        val fgOffset = (width - fgSize) / 2
        if (foregroundFile.extension == "svg") {
            val fgImg = renderSvgToImage(foregroundFile, fgSize, fgSize)
            g.drawImage(fgImg, fgOffset, fgOffset, null)
        } else {
            val fgImg = ImageIO.read(foregroundFile)
            g.drawImage(fgImg, fgOffset, fgOffset, fgSize, fgSize, null)
        }

        if (isRounded) {
            val rounded = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
            val g2 = rounded.createGraphics()
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
            )
            g2.fill(Ellipse2D.Double(0.0, 0.0, width.toDouble(), height.toDouble()))
            g2.dispose()
            g.composite = AlphaComposite.DstIn
            g.drawImage(rounded, 0, 0, null)
        }
    } else {
        if (foregroundFile.extension == "svg") {
            val fgImg = renderSvgToImage(foregroundFile, width, height)
            g.drawImage(fgImg, 0, 0, null)
        } else {
            val fgImg = ImageIO.read(foregroundFile)
            g.drawImage(fgImg, 0, 0, width, height, null)
        }
    }

    g.dispose()
    return combined
}

/**
 * Parses a hex colour string into an AWT [Color].
 *
 * Supported formats:
 * - `#RGB` (3 hex digits, each doubled)
 * - `#RRGGBB` (6 hex digits)
 * - `#AARRGGBB` (8 hex digits)
 * - Same formats without the leading `#`
 *
 * @param hexColor The colour string to parse.
 * @return The corresponding [Color]. Returns [Color.WHITE] when the input cannot be parsed.
 */
fun parseHexColor(hexColor: String): Color {
    return try {
        val hex = if (hexColor.startsWith("#")) hexColor.substring(1) else hexColor
        when (hex.length) {
            3 -> {
                val r = hex.take(1).repeat(2).toInt(16)
                val g = hex.substring(1, 2).repeat(2).toInt(16)
                val b = hex.substring(2, 3).repeat(2).toInt(16)
                Color(r, g, b)
            }

            6 -> {
                val r = hex.take(2).toInt(16)
                val g = hex.substring(2, 4).toInt(16)
                val b = hex.substring(4, 6).toInt(16)
                Color(r, g, b)
            }

            8 -> {
                val a = hex.take(2).toInt(16)
                val r = hex.substring(2, 4).toInt(16)
                val g = hex.substring(4, 6).toInt(16)
                val b = hex.substring(6, 8).toInt(16)
                Color(r, g, b, a)
            }

            else -> Color.WHITE
        }
    } catch (e: Exception) {
        Color.WHITE
    }
}
