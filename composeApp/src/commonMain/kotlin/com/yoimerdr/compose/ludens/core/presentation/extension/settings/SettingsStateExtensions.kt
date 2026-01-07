package com.yoimerdr.compose.ludens.core.presentation.extension.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.yoimerdr.compose.ludens.core.domain.factory.SettingsFactory
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemTheme
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.PositionableItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SettingsState
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.dark
import ludens.composeapp.generated.resources.light
import ludens.composeapp.generated.resources.system_default
import org.jetbrains.compose.resources.stringResource

/**
 * Type alias representing a pair of a positionable item and its associated control item.
 *
 * This pairing allows for easy management of UI elements that have both position and control properties.
 */
typealias PositionableControlItem = Pair<PositionableItemState, ControlItemState>

/**
 * Extension property providing the default settings state.
 *
 * This property creates a default [SettingsState] by converting the domain layer's
 * default settings factory output to a UI model.
 *
 * @return The default [SettingsState] instance.
 */
val SettingsState.Companion.default: SettingsState
    get() = SettingsFactory.settings()
        .toUIModel()

/**
 * Filters a list of positionable control items to get only enabled items of specific types.
 *
 * This extension function filters the list to include only enabled items whose type
 * matches the specified [ItemType] values.
 *
 * @param type The primary [ItemType] to filter by.
 * @param types Additional [ItemType] values to include in the filter.
 * @return A filtered list containing only enabled items matching the specified types.
 */
fun List<PositionableControlItem>.getEnabled(
    type: ItemType,
    vararg types: ItemType,
): List<PositionableControlItem> {
    val source = (types.toSet() + type)
    return this.filter {
        val item = it.second

        item.enabled && item.type in source
    }
}

/**
 * Combines a list of control items with their corresponding positionable items.
 *
 * This extension function creates a list of [PositionableControlItem] pairs by matching
 * control items with their corresponding position data. Items without position data are omitted.
 *
 * @param items The list of [PositionableItemState] containing position information.
 * @return A list of paired [PositionableControlItem] where each control item is matched with its position.
 */
fun List<ControlItemState>.withPositionable(
    items: List<PositionableItemState>,
): List<PositionableControlItem> {
    val source = items.associateBy { it.type }

    return this.mapNotNull { item ->
        source[item.type.toPositionable()]?.let {
            it to item
        }
    }
}

/**
 * Determines whether to use the dark theme.
 *
 * This evaluates the current theme setting and returns whether
 * dark mode should be applied. For [SystemTheme.System], it queries the system's
 * current theme preference.
 *
 * @see isSystemInDarkTheme
 * @return `true` if dark theme should be used, `false` for light theme.
 */
val SystemTheme.isDarkTheme: Boolean
    @Composable
    @ReadOnlyComposable
    get() {
        return when (this) {
            SystemTheme.System -> isSystemInDarkTheme()
            SystemTheme.Light -> false
            SystemTheme.Dark -> true
        }
    }

val SystemTheme.label: String
    @Composable
    get() {
        return when (this) {
            SystemTheme.Light -> stringResource(Res.string.light)
            SystemTheme.Dark -> stringResource(Res.string.dark)
            SystemTheme.System -> stringResource(Res.string.system_default)
        }
    }