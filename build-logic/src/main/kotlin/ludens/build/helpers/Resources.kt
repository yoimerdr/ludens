package ludens.build.helpers

import java.io.File

/**
 * Set of Kotlin reserved words that cannot be used as raw identifiers in generated source code.
 *
 * When a generated resource name matches one of these keywords, the code generator
 * wraps it in backticks (`` ` ``) to produce valid Kotlin.
 */
val KotlinKeywords = setOf(
    "as",
    "break",
    "class",
    "continue",
    "do",
    "else",
    "false",
    "for",
    "fun",
    "if",
    "in",
    "interface",
    "is",
    "null",
    "object",
    "package",
    "return",
    "super",
    "this",
    "throw",
    "true",
    "try",
    "typealias",
    "typeof",
    "val",
    "var",
    "when",
    "while"
)

/**
 * Normalizes an arbitrary string into a valid Kotlin identifier suitable for use as a
 * generated resource accessor name.
 *
 * ### Normalization rules:
 * 1. Lowercases the entire input.
 * 2. Replaces any character outside `[A-Za-z0-9_]` with `_`.
 * 3. Prefixes `_` when the result starts with a digit (Kotlin identifiers cannot begin with a digit).
 * 4. Wraps the result in backticks if it matches a [KotlinKeywords | Kotlin keyword].
 *
 * @param raw The raw resource name to normalize.
 * @return A Kotlin-safe identifier string.
 *
 * Examples:
 * ```
 * parseResourceName("My Resource")       // → "my_resource"
 * parseResourceName("123foo")            // → "_123foo"
 * parseResourceName("class")             // → "`class`"
 * parseResourceName("my-image.png")      // → "my_image_png"
 * ```
 */
fun parseResourceName(raw: String): String {
    return raw.lowercase().replace(Regex("[^a-zA-Z0-9_]"), "_").let {
        val result = if (it.firstOrNull()?.isDigit() == true) "_$it"
        else it

        if (result in KotlinKeywords) "`$result`" else result
    }
}

/**
 * Converts a filename into a Kotlin-safe resource name by stripping the file extension
 * before normalizing.
 *
 * @param filename The source filename (e.g., `"my-font.ttf"`, `"icon@2x.png"`).
 * @return A Kotlin-safe identifier derived from the name without extension.
 *
 * Example:
 * ```
 * parseResourceNameFromFilename("my-font.ttf")  // → "my_font"
 * parseResourceNameFromFilename("icon@2x.png")  // → "icon_2x"
 * ```
 */
fun parseResourceNameFromFilename(filename: String): String {
    return parseResourceName(filename.substringBeforeLast("."))
}


fun File.deleteIfExists() {
    if (exists()) delete()
}