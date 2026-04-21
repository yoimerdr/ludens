package com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key

/**
 * Represents keyboard keys used for input-related operations in scripts.
 *
 * Each key is associated with a specific keyboard code that can be used to handle
 * user input actions, including movement, control, and command keys.
 */
enum class InputKey(
    override val code: Int,
) : KeyboardKey {
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
     * The Page Up key with keyboard code 33.
     */
    PageUp(33),

    /**
     * The Page Down key with keyboard code 34.
     */
    PageDown(34),

    /**
     * The Home key with keyboard code 36.
     */
    Home(36),

    /**
     * The End key with keyboard code 35.
     */
    End(35),

    /**
     * The Insert key with keyboard code 45.
     */
    Insert(45),

    /**
     * The Delete key with keyboard code 46.
     */
    Delete(46),

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
    Alt(18),

    /**
     * The Backspace key with keyboard code 8.
     */
    Backspace(8),

    /**
     * The Tab key with keyboard code 9.
     */
    Tab(9),

    /**
     * The Escape key with keyboard code 27.
     */
    Escape(27),

    /**
     * The Enter key with keyboard code 13.
     */
    Enter(13),

    /**
     * The Space key with keyboard code 32.
     */
    Space(32),

    /**
     * The F9 key with keyboard code 120.
     */
    F9(120),

    /**
     * The numpad 0 key with keyboard code 96.
     */
    Numpad0(96),

    /**
     * The numpad 1 key with keyboard code 97.
     */
    Numpad1(97),

    /**
     * The numpad 2 key with keyboard code 98.
     */
    Numpad2(98),

    /**
     * The numpad 3 key with keyboard code 99.
     */
    Numpad3(99),

    /**
     * The numpad 4 key with keyboard code 100.
     */
    Numpad4(100),

    /**
     * The numpad 5 key with keyboard code 101.
     */
    Numpad5(101),

    /**
     * The numpad 6 key with keyboard code 102.
     */
    Numpad6(102),

    /**
     * The numpad 7 key with keyboard code 103.
     */
    Numpad7(103),

    /**
     * The numpad 8 key with keyboard code 104.
     */
    Numpad8(104),

    /**
     * The numpad 9 key with keyboard code 105.
     */
    Numpad9(105),

    /**
     * The 0 key with keyboard code 48.
     */
    Digit0(48),

    /**
     * The 1 key with keyboard code 49.
     */
    Digit1(49),

    /**
     * The 2 key with keyboard code 50.
     */
    Digit2(50),

    /**
     * The 3 key with keyboard code 51.
     */
    Digit3(51),

    /**
     * The 4 key with keyboard code 52.
     */
    Digit4(52),

    /**
     * The 5 key with keyboard code 53.
     */
    Digit5(53),

    /**
     * The 6 key with keyboard code 54.
     */
    Digit6(54),

    /**
     * The 7 key with keyboard code 55.
     */
    Digit7(55),

    /**
     * The 8 key with keyboard code 56.
     */
    Digit8(56),

    /**
     * The 9 key with keyboard code 57.
     */
    Digit9(57),

    /**
     * The A key with keyboard code 65.
     */
    KeyA(65),

    /**
     * The B key with keyboard code 66.
     */
    KeyB(66),

    /**
     * The C key with keyboard code 67.
     */
    KeyC(67),

    /**
     * The D key with keyboard code 68.
     */
    KeyD(68),

    /**
     * The E key with keyboard code 69.
     */
    KeyE(69),

    /**
     * The F key with keyboard code 70.
     */
    KeyF(70),

    /**
     * The G key with keyboard code 71.
     */
    KeyG(71),

    /**
     * The H key with keyboard code 72.
     */
    KeyH(72),

    /**
     * The I key with keyboard code 73.
     */
    KeyI(73),

    /**
     * The J key with keyboard code 74.
     */
    KeyJ(74),

    /**
     * The K key with keyboard code 75.
     */
    KeyK(75),

    /**
     * The L key with keyboard code 76.
     */
    KeyL(76),

    /**
     * The M key with keyboard code 77.
     */
    KeyM(77),

    /**
     * The N key with keyboard code 78.
     */
    KeyN(78),

    /**
     * The O key with keyboard code 79.
     */
    KeyO(79),

    /**
     * The P key with keyboard code 80.
     */
    KeyP(80),

    /**
     * The Q key with keyboard code 81.
     */
    KeyQ(81),

    /**
     * The R key with keyboard code 82.
     */
    KeyR(82),

    /**
     * The S key with keyboard code 83.
     */
    KeyS(83),

    /**
     * The T key with keyboard code 84.
     */
    KeyT(84),

    /**
     * The U key with keyboard code 85.
     */
    KeyU(85),

    /**
     * The V key with keyboard code 86.
     */
    KeyV(86),

    /**
     * The W key with keyboard code 87.
     */
    KeyW(87),

    /**
     * The X key with keyboard code 88.
     */
    KeyX(88),

    /**
     * The Y key with keyboard code 89.
     */
    KeyY(89),

    /**
     * The Z key with keyboard code 90.
     */
    KeyZ(90);

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
            get() = entries
                .toSet() - movements
    }
}
