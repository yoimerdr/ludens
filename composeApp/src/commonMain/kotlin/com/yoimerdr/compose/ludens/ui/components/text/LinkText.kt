package com.yoimerdr.compose.ludens.ui.components.text

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.TextUnit

/**
 * Represents a clickable link portion within a text string.
 *
 * This defines a specific substring of text that should be rendered
 * as a clickable hyperlink with an associated URL.
 *
 * @property part The text substring that will become a clickable link.
 * @property url The URL to navigate to when the link is clicked.
 *
 * @see LinkText
 */
@Immutable
data class LinkTextPart(
    val part: String,
    val url: String,
)

/**
 * Data model for text containing multiple hyperlinks.
 *
 * This encapsulates a text string and a list of clickable link portions
 * within that text. Each link is defined by a [LinkTextPart] that specifies which
 * substring should be clickable and its target URL.
 *
 * @property text The complete text string that may contain one or more hyperlinks.
 * @property links A list of [LinkTextPart] objects defining the clickable portions
 * within the text and their corresponding URLs.
 *
 * @see LinkTextPart
 */
@Immutable
data class LinkText(
    val text: String,
    val links: List<LinkTextPart>,
)

/**
 * Displays text with embedded clickable hyperlinks.
 *
 * This composable renders text that contains one or more clickable hyperlinks.
 * Each link is defined in the [LinkText] data model with a specific substring
 * and target URL. The component automatically finds and annotates the specified
 * substrings within the text, making them clickable with custom styling.
 *
 * @param text The [LinkText] model containing the text string and link definitions.
 * @param modifier The modifier to be applied to the text.
 * @param linkStyle The [TextLinkStyles] to apply to clickable links. Defaults to
 * primary color with underline decoration following Material Design guidelines.
 * @param color The color to apply to the text. Defaults to [Color.Unspecified].
 * @param autoSize Optional [TextAutoSize] configuration for automatic text sizing.
 * @param fontSize The font size to apply to the text. Defaults to [TextUnit.Unspecified].
 * @param fontStyle The font style (normal or italic) to apply. Defaults to `null`.
 * @param fontWeight The font weight (e.g., bold) to apply. Defaults to `null`.
 * @param fontFamily The font family to apply. Defaults to `null`.
 * @param letterSpacing The spacing between characters. Defaults to [TextUnit.Unspecified].
 * @param textDecoration The decoration (e.g., underline) to apply. Defaults to `null`.
 * @param textAlign The horizontal alignment of the text. Defaults to `null`.
 * @param lineHeight The height of each line of text. Defaults to [TextUnit.Unspecified].
 * @param overflow How to handle text overflow. Defaults to [TextOverflow.Clip].
 * @param softWrap Whether to wrap text at soft line breaks. Defaults to `true`.
 * @param maxLines The maximum number of lines to display. Defaults to [Int.MAX_VALUE].
 * @param minLines The minimum number of lines to display. Defaults to `1`.
 * @param inlineContent A map of inline content placeholders. Defaults to an empty map.
 * @param onTextLayout Callback invoked when text layout is complete.
 * @param style The base [TextStyle] to apply. Defaults to the current [LocalTextStyle].
 *
 * @see LinkText
 * @see LinkTextPart
 * @see Text
 */
@Composable
fun LinkText(
    text: LinkText,
    modifier: Modifier = Modifier,
    linkStyle: TextLinkStyles = TextLinkStyles(
        style = SpanStyle(
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline
        )
    ),
    color: Color = Color.Unspecified,
    autoSize: TextAutoSize? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
) {
    val annotatedText = buildAnnotatedString {
        var lastIndex = 0
        val (text, links) = text

        if (links.isEmpty()) {
            append(text)
        } else {
            links.forEach { (part, url) ->
                val startIndex = text.indexOf(part, startIndex = lastIndex)

                if (startIndex >= 0) {
                    val endIndex = startIndex + part.length
                    append(text.substring(lastIndex, startIndex))

                    val linkAnnotation = LinkAnnotation.Url(
                        url = url,
                        styles = linkStyle
                    )

                    withLink(linkAnnotation) {
                        append(part)
                    }

                    lastIndex = endIndex
                }
            }

            if (lastIndex < text.length) {
                append(text.substring(lastIndex))
            }
        }
    }

    Text(
        text = annotatedText,
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
        inlineContent = inlineContent,
        onTextLayout = onTextLayout,
    )
}


/**
 * Displays text with a single clickable hyperlink.
 *
 * This is a convenience overload for displaying text with a single hyperlink.
 * You can specify which part of the text should be clickable, or make the entire
 * text clickable by omitting the [part] parameter. The specified portion becomes
 * a hyperlink with the provided URL and custom styling.
 *
 * @param text The complete text string to display.
 * @param url The URL to navigate to when the link is clicked.
 * @param modifier The modifier to be applied to the text.
 * @param part The specific substring within [text] that should be clickable.
 * If `null`, the entire text becomes a clickable link. Defaults to `null`.
 * @param linkStyle The [TextLinkStyles] to apply to the clickable link. Defaults to
 * primary color with underline decoration following Material Design guidelines.
 * @param color The color to apply to the text. Defaults to [Color.Unspecified].
 * @param autoSize Optional [TextAutoSize] configuration for automatic text sizing.
 * @param fontSize The font size to apply to the text. Defaults to [TextUnit.Unspecified].
 * @param fontStyle The font style (normal or italic) to apply. Defaults to `null`.
 * @param fontWeight The font weight (e.g., bold) to apply. Defaults to `null`.
 * @param fontFamily The font family to apply. Defaults to `null`.
 * @param letterSpacing The spacing between characters. Defaults to [TextUnit.Unspecified].
 * @param textDecoration The decoration (e.g., underline) to apply. Defaults to `null`.
 * @param textAlign The horizontal alignment of the text. Defaults to `null`.
 * @param lineHeight The height of each line of text. Defaults to [TextUnit.Unspecified].
 * @param overflow How to handle text overflow. Defaults to [TextOverflow.Clip].
 * @param softWrap Whether to wrap text at soft line breaks. Defaults to `true`.
 * @param maxLines The maximum number of lines to display. Defaults to [Int.MAX_VALUE].
 * @param minLines The minimum number of lines to display. Defaults to `1`.
 * @param inlineContent A map of inline content placeholders. Defaults to an empty map.
 * @param onTextLayout Callback invoked when text layout is complete.
 * @param style The base [TextStyle] to apply. Defaults to the current [LocalTextStyle].
 *
 * @see LinkText
 * @see LinkTextPart
 * @see Text
 */
@Composable
fun LinkText(
    text: String,
    url: String,
    modifier: Modifier = Modifier,
    part: String? = null,
    linkStyle: TextLinkStyles = TextLinkStyles(
        style = SpanStyle(
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline
        )
    ),
    color: Color = Color.Unspecified,
    autoSize: TextAutoSize? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
) {
    LinkText(
        text = LinkText(
            text = text,
            links = listOf(
                LinkTextPart(
                    part = part ?: text,
                    url = url
                )
            )
        ),
        modifier = modifier,
        style = style,
        color = color,
        linkStyle = linkStyle,
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
        inlineContent = inlineContent,
        onTextLayout = onTextLayout
    )
}
