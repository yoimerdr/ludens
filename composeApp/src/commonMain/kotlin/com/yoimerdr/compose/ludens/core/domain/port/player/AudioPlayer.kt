package com.yoimerdr.compose.ludens.core.domain.port.player

import com.yoimerdr.compose.ludens.core.domain.value.Volume

/**
 * Port interface for audio player functionality.
 *
 * This interface defines the contract for controlling audio playback,
 * including mute/unmute operations and volume control.
 * Implementations should handle the platform-specific audio management.
 */
interface AudioPlayer {
    /** Mutes the audio output. */
    fun mute()

    /** Unmutes the audio output. */
    fun unmute()

    /**
     * Sets the audio volume level.
     *
     * @param level The desired volume level (0-100).
     */
    fun volume(level: Volume)

    /** Returns whether the audio is currently muted. */
    val isMuted: Boolean
}