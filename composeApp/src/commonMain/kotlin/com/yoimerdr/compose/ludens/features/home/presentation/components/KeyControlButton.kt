package com.yoimerdr.compose.ludens.features.home.presentation.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.ui.components.buttons.OutlinedIconButton

/**
 * Displays an action button that detects press and release gestures.
 *
 * The button shows text in the center and emits key events for both press (Down) and
 * release (Up) actions. It includes ripple interaction feedback for visual response
 * to user touch events.
 *
 * @param modifier The modifier to be applied to the button
 * @param text The text to display in the center of the button
 * @param onClick Callback invoked with [KeyEventType.Down] on press and [KeyEventType.Up] on release
 */
@Composable
fun KeyControlButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (KeyEventType) -> Unit,
) {
    val source = remember { MutableInteractionSource() }
    OutlinedIconButton(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .indication(source, ripple())
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            onClick(KeyEventType.Down)
                            source.emit(PressInteraction.Press(it))
                            try {
                                awaitRelease()
                            } finally {
                                source.emit(PressInteraction.Release(PressInteraction.Press(it)))
                                onClick(KeyEventType.Up)
                            }
                        },
                    )
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}