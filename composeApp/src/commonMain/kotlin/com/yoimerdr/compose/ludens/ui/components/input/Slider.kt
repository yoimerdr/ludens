package com.yoimerdr.compose.ludens.ui.components.input

import androidx.annotation.IntRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Slider as MaterialSlider

/**
 * A slider component that allows users to make selections from a range of values.
 *
 * This is an implementation of the slider component from the Material Design 2 specification,
 * overriding the thumb and track components from the default implementation.
 *
 * @param state The state of the slider.
 * @param modifier The modifier to be applied to the slider.
 * @param enabled Controls the enabled state of the slider. When `false`, the user cannot interact with it.
 * @param colors [SliderColors] that will be used to resolve the colors used for this slider in different states. See [SliderDefaults.colors].
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this slider. You can create and pass in your own `remember`ed instance to observe interactions and customize the appearance / behavior of this slider in different states.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Slider(
    state: SliderState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SliderColors = SliderDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val schema = MaterialTheme.colorScheme
    var thumbColor = schema.primary
    var activeTrackerColor = thumbColor
    var inactiveTrackerColor = schema.surfaceVariant
    if (!enabled) {
        thumbColor = schema.onSurface.copy(alpha = 0.38f)
            .compositeOver(schema.surface)
        activeTrackerColor = schema.onSurface
            .copy(alpha = 0.38f)

        inactiveTrackerColor = schema.onSurface
            .copy(alpha = 0.12f)
    }
    MaterialSlider(
        state = state,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        colors = colors,
        thumb = {
            Box(
                modifier = Modifier
                    .size(20.dp) // Standard M2 thumb size
                    .clip(CircleShape)
                    .hoverable(interactionSource)
                    .background(thumbColor)
            )
        },
        track = { sliderState ->

            val fraction = (sliderState.value - sliderState.valueRange.start) /
                    (sliderState.valueRange.endInclusive - sliderState.valueRange.start)

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            ) {
                val width = size.width
                val centerY = size.height / 2
                val activeWidth = width * fraction

                // Dibujar parte inactiva (la línea gris de fondo)
                drawLine(
                    color = inactiveTrackerColor,
                    start = Offset(activeWidth, centerY),
                    end = Offset(width, centerY),
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round
                )

                // Dibujar parte activa (la línea de color)
                drawLine(
                    color = activeTrackerColor,
                    start = Offset(0f, centerY),
                    end = Offset(activeWidth, centerY),
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
    )
}

/**
 * A slider component that allows users to make selections from a range of values.
 *
 * This is an implementation of the slider component from the Material Design 2 specification,
 * overriding the thumb and track components from the default implementation.
 *
 * @param steps The number of discrete steps the slider can be moved to.
 * @param valueRange The range of values that the slider can be set to.
 * @param modifier The modifier to be applied to the slider.
 * @param value The current value of the slider.
 * @param onValueChange The callback that is invoked when the value of the slider changes.
 * @param enabled Controls the enabled state of the slider. When `false`, the user cannot interact with it.
 * @param colors [SliderColors] that will be used to resolve the colors used for this slider in different states. See [SliderDefaults.colors].
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this slider. You can create and pass in your own `remember`ed instance to observe interactions and customize the appearance / behavior of this slider in different states.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Slider(
    @IntRange(from = 0) steps: Int = 0,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    enabled: Boolean = true,
    onValueChangeFinished: (() -> Unit)? = null,
    colors: SliderColors = SliderDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val state =
        remember(steps, valueRange) { SliderState(value, steps, onValueChangeFinished, valueRange) }
    state.onValueChangeFinished = onValueChangeFinished
    state.onValueChange = onValueChange
    state.value = value

    Slider(
        state = state,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
    )
}
