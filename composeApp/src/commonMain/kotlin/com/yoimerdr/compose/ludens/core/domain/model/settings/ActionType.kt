package com.yoimerdr.compose.ludens.core.domain.model.settings

/**
 * Enum representing types of quick actions.
 *
 * @property value The integer value associated with this action type.
 */
enum class ActionType(val value: Int) {
    /** Toggle the visibility of on-screen controls */
    ToggleControls(0),

    /** Toggle audio mute/unmute */
    ToggleMute(1),

    /** Toggle FPS counter display */
    ToggleFPS(2),

    /**
     * Opens the settings options.
     *
     * This action type must never be disabled.
     * */
    Settings(3);

    companion object {
        /**
         * Retrieves an ActionType from its integer value.
         *
         * @param value The integer value to convert.
         * @return The corresponding ActionType.
         * @throws NoSuchElementException if no matching type is found.
         */
        fun from(value: Int): ActionType = entries.first { value == it.value }
    }
}