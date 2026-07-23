package com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key

/**
 * Represents keyboard keys used for graphics-related operations in scripts.
 *
 * Each key is associated with a specific keyboard code that can be used to trigger
 * graphics-related actions or commands.
 *
 */
enum class GraphicsKey(
    override val code: Int,
) : KeyboardKey {
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
    F4(115);

    companion object {
        /**
         * Gets an [GraphicsKey] from a key code.
         *
         * @param code The key code.
         * @return The corresponding [GraphicsKey], or `null` if not found.
         */
        fun from(code: Int): GraphicsKey? {
            return GraphicsKey.entries.firstOrNull { it.code == code }
        }
    }
}