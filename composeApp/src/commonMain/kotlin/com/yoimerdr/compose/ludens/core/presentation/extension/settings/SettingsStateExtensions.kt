package com.yoimerdr.compose.ludens.core.presentation.extension.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.yoimerdr.compose.ludens.core.domain.factory.SettingsFactory
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionType
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemTheme
import com.yoimerdr.compose.ludens.core.infrastructure.extension.key.toInputKey
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ActionItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlKeyItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.PositionableItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SettingsState
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.dark
import ludens.composeapp.generated.resources.en
import ludens.composeapp.generated.resources.es
import ludens.composeapp.generated.resources.key_button
import ludens.composeapp.generated.resources.key_joystick
import ludens.composeapp.generated.resources.key_settings
import ludens.composeapp.generated.resources.light
import ludens.composeapp.generated.resources.system_default
import ludens.composeapp.generated.resources.system_language
import ludens.composeapp.generated.resources.unknown
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
 * Filters a list of action items to get only enabled items of specific types.
 *
 * This extension function filters the list to include only enabled items whose type
 * matches the specified [ActionType] values.
 *
 * @param type The primary [ActionType] to filter by.
 * @param types Additional [ActionType] values to include in the filter.
 * @return A filtered list containing only enabled items matching the specified types.
 */
fun List<ActionItemState>.getEnabled(
    type: ActionType,
    vararg types: ActionType,
): List<ActionItemState> {
    val source = setOf(type) + types

    return filter {
        it.enabled && it.type in source
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

/**
 * Gets the localized label string for the current [SystemTheme].
 *
 * This extension property provides a user-friendly, localized display name.
 */
val SystemTheme.label: String
    @Composable
    get() {
        return when (this) {
            SystemTheme.Light -> stringResource(Res.string.light)
            SystemTheme.Dark -> stringResource(Res.string.dark)
            SystemTheme.System -> stringResource(Res.string.system_default)
        }
    }

/**
 * Gets the localized label string for the current [SystemLanguage].
 *
 * This extension property provides a user-friendly, localized display name.
 */
val SystemLanguage.label: String
    @Composable
    get() = when (this) {
        SystemLanguage.English -> stringResource(Res.string.en)
        SystemLanguage.Spanish -> stringResource(Res.string.es)
        else -> stringResource(Res.string.system_language)
    }

/**
 * Gets the localized label string for the current [ControlItemState].
 *
 * This extension property generates a descriptive label for control items based on their type.
 * For key-based controls, it includes the key binding information. For other control types,
 * it returns their standard localized names.
 *
 * The label format varies by item type:
 * - For key types (A, B, X, Y, etc.): Returns a formatted string
 * - For [ItemType.Joystick]: Returns "Joystick"
 * - For [ItemType.Settings]: Returns "Settings"
 * - For unknown types: Returns "Unknown"
 */
val ControlItemState.label: String
    @Composable
    get() {
        val keys = ItemType.keys
        val type = type
        val unknow = stringResource(Res.string.unknown)
        if (type in keys) {
            val defaultKey = type
                .toInputKey()

            val input = when (this) {
                is ControlKeyItemState -> this.key
                else -> null
            } ?: defaultKey

            return stringResource(
                Res.string.key_button,
                type.simpleName,
                input?.name?.ifBlank { unknow } ?: unknow
            )
        }

        return when (type) {
            ItemType.Joystick -> stringResource(Res.string.key_joystick)
            ItemType.Settings -> stringResource(Res.string.key_settings)
            else -> unknow
        }

    }