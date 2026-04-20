package com.yoimerdr.compose.ludens.ui.components.text

import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Displays small-sized text with predefined styling.
 *
 * This composable is a convenience wrapper around [Text] that provides default
 * styling for small text elements such as captions, labels, or helper text.
 * It uses customized default values for font size (11sp), letter spacing (0.4sp),
 * and line height (16sp).
 *
 * @param text The text string to display.
 * @param modifier The modifier to be applied to the text.
 * @param color The color to apply to the text. Defaults to [Color.Unspecified].
 * @param autoSize Optional [TextAutoSize] configuration for automatic text sizing.
 * @param fontSize The font size to apply to the text. Defaults to `11.sp` for small text.
 * @param fontStyle The font style (normal or italic) to apply. Defaults to `null`.
 * @param fontWeight The font weight (e.g., bold) to apply. Defaults to `null`.
 * @param fontFamily The font family to apply. Defaults to `null`.
 * @param letterSpacing The spacing between characters. Defaults to `0.4.sp` for improved
 * readability at small sizes.
 * @param textDecoration The decoration (e.g., underline) to apply. Defaults to `null`.
 * @param textAlign The horizontal alignment of the text. Defaults to `null`.
 * @param lineHeight The height of each line of text. Defaults to `16.sp`.
 * @param overflow How to handle text overflow. Defaults to [TextOverflow.Clip].
 * @param softWrap Whether to wrap text at soft line breaks. Defaults to `true`.
 * @param maxLines The maximum number of lines to display. Defaults to [Int.MAX_VALUE].
 * @param minLines The minimum number of lines to display. Defaults to `1`.
 * @param onTextLayout Callback invoked when text layout is complete. Defaults to `null`.
 * @param style The base [TextStyle] to apply. Defaults to [MaterialTheme.typography]'s bodySmall.
 *
 * @see Text
 * @see MaterialTheme.typography
 */
@Composable
fun SmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    autoSize: TextAutoSize? = null,
    fontSize: TextUnit = 11.sp,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = 0.4.sp,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = 16.sp,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = MaterialTheme.typography.bodySmall,
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color,
        autoSize = autoSize,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
    )
}