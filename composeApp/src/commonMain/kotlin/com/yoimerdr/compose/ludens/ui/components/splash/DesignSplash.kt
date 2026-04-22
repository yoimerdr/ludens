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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/**
 * Displays the splash logo with a game-like floating entrance and pulse.
 *
 * The animation keeps the logo centered while applying a subtle rise, scale, and alpha pulse.
 * It is intentionally less rigid than a product-site motion system and works better as a game
 * launcher splash.
 *
 * @param modifier The modifier applied to the splash container.
 * @param imagePainter The painter used to render the logo.
 * @param contentDescription Accessibility description for the logo.
 * @param maxSize Maximum rendered size for the logo.
 * @param duration Duration in milliseconds for each animation cycle.
 */
@Composable
fun DesignSplash(
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    contentDescription: String?,
    maxSize: DpSize = 200.dp.let { DpSize(it, it) },
    duration: Int = 2000,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "DesignSplashTransition")

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.90f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale",
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.58f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration / 2, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
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
                    transformOrigin = TransformOrigin.Center
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                }
        )
    }
}
