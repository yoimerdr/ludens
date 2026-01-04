package com.yoimerdr.compose.ludens.features.home.presentation.components

import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.IntOffset
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableType
import com.yoimerdr.compose.ludens.core.domain.value.Alpha
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.PositionableControlItem
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlActionItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.PositionableItemState
import com.yoimerdr.compose.ludens.ui.components.buttons.OutlinedIconButton
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.Settings
import kotlin.math.roundToInt

/**
 * Displays the configuration/settings button.
 *
 * The button exact position and appearance can be customized through the [settings] parameter.
 * If no settings are provided, default values are used with medium alpha transparency.
 *
 * @param onConfiguration Callback invoked when the configuration button is clicked
 * @param settings Optional positionable control item configuration that defines the button's
 * position (x, y coordinates) and appearance (enabled state, alpha transparency).
 * If null, defaults to offset position (0, 0) with medium alpha.
 *
 * @see Alpha.Medium
 */
@Composable
fun ConfigurationButton(
    modifier: Modifier = Modifier,
    onConfiguration: () -> Unit,
    settings: PositionableControlItem?,
) {
    val settings = settings ?: (
            PositionableItemState(
                type = PositionableType.Settings,
                x = 0f,
                y = 0f
            ) to ControlActionItemState(
                enabled = true,
                type = ItemType.Settings,
                alpha = Alpha.Medium.value
            )
            )

    OutlinedIconButton(
        border = null,
        onClick = onConfiguration,
        modifier = modifier
            .offset {
                val position = settings.first

                IntOffset(
                    x = position.x.roundToInt(),
                    y = position.y.roundToInt(),
                )
            }
            .alpha(settings.second.alpha)
    ) {
        Icon(
            LudensIcons.Default.Settings,
            contentDescription = "Settings",
        )
    }
}