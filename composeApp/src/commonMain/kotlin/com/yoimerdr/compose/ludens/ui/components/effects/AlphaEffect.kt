package com.yoimerdr.compose.ludens.ui.components.effects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yoimerdr.compose.ludens.ui.state.effects.FloatEffect
import com.yoimerdr.compose.ludens.ui.state.effects.StackedFloatEffect
import com.yoimerdr.compose.ludens.ui.state.effects.getValue

/**
 * Displays an effect overlay using a [AlphaEffect] for alpha animation.
 *
 * The effect renders a colored overlay on top of the content with an alpha value
 * controlled by the provided effect. The overlay is only displayed when the alpha value
 * is greater than 0. This is typically used for visual feedback like screen flashes,
 * damage indicators, or transition effects.
 *
 * @param effect The [AlphaEffect] that controls the alpha value of the overlay.
 * @param modifier The [Modifier] to apply to the overlay box.
 * @param color The base color of the overlay.
 * @param content Optional content to display within the overlay box.
 *
 * @see AlphaEffect
 * @see Box
 */
@Composable
fun AlphaEffect(
    effect: FloatEffect,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    content: (@Composable BoxScope.() -> Unit)? = null,
) {
    val alpha by effect

    if (alpha > 0f) {
        Box(
            modifier = modifier.fillMaxSize()
                .background(color.copy(alpha = alpha)),
            content = content ?: {}
        )
    }
}

/**
 * Displays an effect overlay using a [StackedFloatEffect].
 *
 * This is a convenience overload that accepts a [StackedFloatEffect] and delegates to the
 * base [AlphaEffect] function using its underlying [AlphaEffect].
 *
 * @param effect The [StackedFloatEffect] that controls the alpha value of the overlay.
 * @param modifier The [Modifier] to apply to the overlay box.
 * @param color The base color of the overlay.
 * @param content Optional content to display within the overlay box.
 *
 * @see StackedFloatEffect
 * @see AlphaEffect
 */
@Composable
fun AlphaEffect(
    effect: StackedFloatEffect,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    content: (@Composable BoxScope.() -> Unit)? = null,
) {
    AlphaEffect(
        modifier = modifier,
        color = color,
        effect = effect.root,
        content = content
    )
}