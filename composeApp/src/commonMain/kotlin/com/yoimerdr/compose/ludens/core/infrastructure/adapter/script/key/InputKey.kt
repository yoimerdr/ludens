package com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key

/**
 * Represents keyboard keys used for input-related operations in scripts.
 *
 * Each key is associated with a specific keyboard code that can be used to handle
 * user input actions, including movement, control, and command keys.
 *
 * @property code The keyboard code associated with the key.
 */
enum class InputKey(
    val code: Int,
) {
    /**
     * The Enter key with keyboard code 13.
     */
    Enter(13),

    /**
     * The Escape key with keyboard code 27.
     */
    Escape(27),

    /**
     * The Up arrow key with keyboard code 38.
     */
    Up(38),

    /**
     * The Down arrow key with keyboard code 40.
     */
    Down(40),

    /**
     * The Left arrow key with keyboard code 37.
     */
    Left(37),

    /**
     * The Right arrow key with keyboard code 39.
     */
    Right(39),

    /**
     * The Shift key with keyboard code 16.
     */
    Shift(16),

    /**
     * The Control key with keyboard code 17.
     */
    Control(17),

    /**
     * The Alt key with keyboard code 18.
     */
    Alt(18);

    companion object {
        /**
         * Gets an [InputKey] from a key code.
         *
         * @param code The key code.
         * @return The corresponding [InputKey], or `null` if not found.
         */
        fun from(code: Int): InputKey? {
            return entries.firstOrNull { it.code == code }
        }

        /**
         * A set of keys representing the up and right movement.
         */
        val upRight: Set<InputKey>
            get() = setOf(Up, Right)

        /**
         * A set of keys representing the up and left movement.
         */
        val upLeft: Set<InputKey>
            get() = setOf(Up, Left)

        /**
         * A set of keys representing the down and right movement.
         */
        val downRight: Set<InputKey>
            get() = setOf(Down, Right)

        /**
         * A set of keys representing the down and left movement.
         */
        val downLeft: Set<InputKey>
            get() = setOf(Down, Left)

        /**
         * A set of keys representing all movement keys.
         */
        val movements: Set<InputKey>
            get() = setOf(Up, Down, Left, Right)

        /**
         * A set of keys representing all control keys.
         */
        val controls: Set<InputKey>
            get() = setOf(
                Enter, Escape, Shift, Control, Alt
            )
    }
}
