package com.yoimerdr.compose.ludens.features.settings.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.components.input.Slider

/**
 * Displays a slider control for adjusting alpha (transparency) values.
 *
 * Combines a slider with a text label showing the current value as a percentage.
 * The slider allows users to adjust values within a specified range, typically used for
 * controlling transparency or opacity.
 *
 * @param modifier The modifier to be applied.
 * @param value The current alpha value to display and control.
 * @param enabled Whether the slider is enabled for interaction. Defaults to true.
 * @param color The color to be applied to the percentage text. Defaults to [Color.Unspecified].
 * @param valueRange The range of values the slider can represent. Defaults to 0f..1f.
 * @param onValueChange Callback invoked when the slider value changes, providing the new value.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlAlpha(
    value: Float,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = Color.Unspecified,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    onValueChange: (Float) -> Unit,
) {
    val state = remember(valueRange) { SliderState(value, valueRange = valueRange) }

    state.onValueChange = onValueChange
    state.value = value

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Slider(
            state = state, enabled = enabled
        )
    }

    Text(
        text = "${(state.value * 100).toInt().toString().padStart(3, ' ')}%",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.width(40.dp),
        textAlign = TextAlign.End,
        color = color
    )
}