package com.yoimerdr.compose.ludens.core.domain.model.settings

/**
 * Represents settings for quick action items.
 *
 * @property enabled Whether the quick action are enabled.
 * @property items The list of quick action items available to the user.
 */
data class ActionSettings(
    val enabled: Boolean,
    val items: List<ActionItem>,
)