package com.yoimerdr.compose.ludens.core.domain.model.settings

/**
 * Represents settings for quick action items.
 *
 * @property items The list of quick action items available to the user.
 */
data class ActionSettings(
    val items: List<ActionItem>
)