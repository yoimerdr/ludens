package com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key

/**
 * Represents keyboard keys used for graphics-related operations in scripts.
 *
 * Each key is associated with a specific keyboard code that can be used to trigger
 * graphics-related actions or commands.
 *
 * @property code The keyboard code associated with the key.
 */
enum class GraphicsKey(
    val code: Int,
) {
    /**
     * F2 function key with keyboard code 113.
     */
    F2(113),

    /**
     * F3 function key with keyboard code 114.
     */
    F3(114),

    /**
     * F4 function key with keyboard code 115.
     */
    F4(115),
}