package com.yoimerdr.compose.ludens.core.domain.model.settings

import com.yoimerdr.compose.ludens.core.domain.value.Alpha

/**
 * Represents settings for on-screen controls.
 *
 * @property enabled Whether the on-screen controls are enabled.
 * @property alpha The default alpha/transparency for all controls.
 * @property items The list of individual control items with their settings.
 * @property positions The list of positionable items with their coordinates.
 */
data class ControlSettings(
    val enabled: Boolean,
    val alpha: Alpha,
    val items: List<ControlItem>,
    val positions: List<PositionableItem>
)