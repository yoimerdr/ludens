package com.yoimerdr.compose.ludens.ui.components.loader

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

/**
 * A composable that displays a circular loading animation by rotating its content.
 *
 * @param label A label for the transition, used for debugging.
 * @param animationDuration The duration of one rotation animation cycle in milliseconds.
 * @param animationEasing The easing function to be used for the rotation animation.
 * @param content The composable to be rotated. It receives a modifier that should be applied to it.
 */
@Composable
fun CircularLoader(
    label: String = "CircularLoader",
    animationDuration: Int = 1000,
    animationEasing: Easing = LinearEasing,
    content: @Composable (Modifier) -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition(label = label)
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = animationEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    content(Modifier.rotate(rotation))
}