package com.yoimerdr.compose.ludens.core.domain.factory

import com.yoimerdr.compose.ludens.core.domain.model.settings.ControlItem
import com.yoimerdr.compose.ludens.core.domain.model.settings.ControlSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableItem
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableType
import com.yoimerdr.compose.ludens.core.domain.model.settings.Settings
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
     * @param control Control settings (default: default control settings).
     * @param positions List of positionable items (default: all positionable types at origin).
     * @return A fully configured Settings instance.
     */
    fun settings(
        tool: ToolSettings = toolSettings(),
        control: ControlSettings = controlSettings(),
        positions: List<PositionableItem> = this.positions(),
    ) = Settings(
        tool = tool,
        control = control,
        positions = positions,
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
     * @return A ControlSettings instance with the specified values.
     */
    fun controlSettings(
        enabled: Boolean = true,
        alpha: Alpha = Alpha.Medium,
        items: List<ControlItem> = controlItems(),
    ) = ControlSettings(
        enabled = enabled,
        alpha = alpha,
        items = items,
    )

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