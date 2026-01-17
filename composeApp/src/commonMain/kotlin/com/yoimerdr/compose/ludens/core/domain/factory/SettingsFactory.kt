package com.yoimerdr.compose.ludens.core.domain.factory

import com.yoimerdr.compose.ludens.core.domain.factory.SettingsFactory.theme
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionItem
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionType
import com.yoimerdr.compose.ludens.core.domain.model.settings.ControlItem
import com.yoimerdr.compose.ludens.core.domain.model.settings.ControlSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableItem
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableType
import com.yoimerdr.compose.ludens.core.domain.model.settings.Settings
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemTheme
import com.yoimerdr.compose.ludens.core.domain.model.settings.ToolSettings
import com.yoimerdr.compose.ludens.core.domain.value.Alpha

/**
 * Factory object for creating default settings instances.
 *
 * This factory provides convenient methods for creating settings objects with
 * sensible defaults, reducing boilerplate code when initializing the application.
 */
object SettingsFactory {
    /**
     * Creates a complete Settings instance with default values.
     *
     * @param tool Tool settings (default: default tool settings).
     * @param control Control settings (default: default control settings with positions).
     * @param theme The application's theme configuration (default: default theme setting).
     * @return A fully configured Settings instance.
     */
    fun settings(
        tool: ToolSettings = toolSettings(),
        control: ControlSettings = controlSettings(),
        system: SystemSettings = systemSettings(),
        action: ActionSettings = actionSettings(),
    ) = Settings(
        tool = tool,
        control = control,
        system = system,
        action = action
    )

    /**
     * Provides the default theme configuration.
     */
    fun theme(): SystemTheme = SystemTheme.System

    /**
     * Provides the default language/locale setting.
     */
    fun language(): SystemLanguage = SystemLanguage.System

    /**
     * Creates default system settings.
     *
     * @param theme The application's theme configuration (default: System).
     * @param language The application's language/locale setting (default: System).
     * @return A SystemSettings instance with the specified values.
     */
    fun systemSettings(
        theme: SystemTheme = this.theme(),
        language: SystemLanguage = this.language(),
    ) = SystemSettings(
        theme = theme,
        language = language,
    )

    /**
     * Creates a default list of positionable items.
     *
     * @return A list containing all positionable types at position (0, 0).
     */
    fun positions() = PositionableType.entries
        .map { PositionableItem(it, 0f, 0f) }

    /**
     * Creates default tool settings.
     *
     * @param isMuted Whether audio is muted (default: false).
     * @param showFPS Whether to show FPS counter (default: false).
     * @return A ToolSettings instance with the specified values.
     */
    fun toolSettings(
        isMuted: Boolean = false,
        showFPS: Boolean = false,
    ) = ToolSettings(
        isMuted = isMuted,
        showFPS = showFPS,
    )

    /**
     * Creates default control settings.
     *
     * @param enabled Whether controls are enabled (default: true).
     * @param alpha The default alpha/transparency for controls (default: Medium).
     * @param items List of control items (default: all control items).
     * @param positions List of positionable items (default: all positionable types at origin).
     * @return A ControlSettings instance with the specified values.
     */
    fun controlSettings(
        enabled: Boolean = true,
        alpha: Alpha = Alpha.Medium,
        items: List<ControlItem> = controlItems(),
        positions: List<PositionableItem> = this.positions(),
    ) = ControlSettings(
        enabled = enabled,
        alpha = alpha,
        items = items,
        positions = positions,
    )

    /**
     * Creates default action settings.
     *
     * @param items The list of action items. Defaults to all available action types.
     * @return An ActionSettings instance with the specified values.
     */
    fun actionSettings(
        items: List<ActionItem> = actionItems(),
    ) = ActionSettings(items = items)

    /**
     * Creates a default list of action items.
     *
     * @return A list containing all action types.
     */
    fun actionItems() = ActionType.entries.map { ActionItem(it, false) }

    /**
     * Creates a default list of control items.
     *
     *
     * @return A list containing all item types, enabled with medium alpha.
     */
    fun controlItems(
    ): List<ControlItem> = ItemType.entries
        .map { ControlItem(it, true, Alpha.Medium) }
}