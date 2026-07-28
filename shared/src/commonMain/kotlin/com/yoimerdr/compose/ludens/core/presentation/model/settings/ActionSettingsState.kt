package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the state of quick action items in the presentation layer.
 *
 * This immutable data class holds the configuration for quick action items
 * that allow users to rapidly toggle application settings and features.
 *
 * @property enabled Whether the quick action are enabled.
 * @property items The list of [ActionItemState] elements representing available quick actions.
 */
@Immutable
data class ActionSettingsState(
    val enabled: Boolean = false,
    val items: ImmutableList<ActionItemState> = persistentListOf(),
)

