package com.yoimerdr.compose.ludens.ui.components.splash

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/**
 * A splash screen with a design that includes a scaling and translating image.
 *
 * @param modifier The modifier to be applied to the component.
 * @param imagePainter The painter for the image to be displayed.
 * @param contentDescription The content description for the image.
 * @param maxSize The maximum size of the image.
 * @param duration The duration of the animation in milliseconds.
 */
@Composable
fun DesignSplash(
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    contentDescription: String?,
    maxSize: DpSize = DpSize(width = 200.dp, height = 200.dp),
    duration: Int = 2000,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "DesignSplashTransition")

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scale",
    )

    val idleScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idleScale"
    )

    val offsetY by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                duration,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = imagePainter,
            contentDescription = contentDescription,
            modifier = Modifier
                .sizeIn(
                    maxWidth = maxSize.width,
                    maxHeight = maxSize.height
                )
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                }
        )

        Image(
            painter = imagePainter,
            contentDescription = contentDescription,
            modifier = Modifier
                .sizeIn(
                    maxWidth = maxSize.width,
                    maxHeight = maxSize.height
                ).fillMaxSize()
                .graphicsLayer {
                    translationY = offsetY
                    scaleX = idleScale
                    scaleY = idleScale
                }
        )
    }
}
