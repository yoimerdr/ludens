package com.yoimerdr.compose.ludens.ui.components.effects

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import com.yoimerdr.compose.ludens.ui.state.effects.ScreenshotEffect
import com.yoimerdr.compose.ludens.ui.state.effects.getValue
import com.yoimerdr.compose.ludens.ui.state.effects.rememberScreenshotEffect
import kotlinx.coroutines.CoroutineScope

/**
 * Applies screenshot effect transformations to a modifier.
 *
 * This modifier applies scale, offset, and alpha transformations from a [ScreenshotEffect]
 * to create animated visual effects. It's typically used to animate content during
 * screenshot capture animations.
 *
 * @param effect The [ScreenshotEffect] that controls the transformations.
 * @param shape The shape to apply clipping and shadow effects.
 * @param origin The transform origin point for scale transformations.
 * @return A [Modifier] with screenshot effect transformations applied.
 *
 * @see ScreenshotEffect
 */
fun Modifier.screenshotEffect(
    effect: ScreenshotEffect,
    shape: Shape = RectangleShape,
    origin: TransformOrigin = TransformOrigin.Center,
): Modifier = this.graphicsLayer {
    // Apply scale transformation
    val scale by effect.asScaleEffect()
    this.scaleX = scale
    this.scaleY = scale

    // Apply position offset from center
    val offset by effect.asOffsetEffect()
    this.translationX = offset.x
    this.translationY = offset.y

    // Apply opacity for fade effects
    val alpha by effect.asHideContent()
    this.alpha = alpha

    transformOrigin = origin

    // Add shadow while flying for depth perception
    this.shape = shape
    this.clip = true
}


/**
 * Displays a screenshot effect container with customizable content and flash animation.
 *
 * This composable creates a base layout for screenshot effects, providing a container
 * with alignment support and customizable flash overlay. It's designed to be used as
 * a foundation for more specific screenshot effect implementations.
 *
 * @param modifier The [Modifier] to apply to the container box.
 * @param alignment The alignment of content within the container.
 * @param scope The coroutine scope used for animations.
 * @param effect The [ScreenshotEffect] that controls the animations.
 * @param flash A composable that renders the flash effect overlay.
 * @param content A composable that renders the main content with access to the [ScreenshotEffect].
 *
 * @see ScreenshotEffect
 * @see AlphaEffect
 * @see rememberScreenshotEffect
 * @see Box
 */
@Composable
fun ScreenshotEffect(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    scope: CoroutineScope = rememberCoroutineScope(),
    effect: ScreenshotEffect = rememberScreenshotEffect(scope),
    flash: @Composable BoxScope.(ScreenshotEffect) -> Unit = {
        AlphaEffect(effect = it.asFlashEffect())
    },
    content: @Composable BoxScope.(ScreenshotEffect) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = alignment
    ) {
        flash(effect)
        content(effect)
    }
}

/**
 * Displays a visual screenshot effect that mimics modern device screenshot animations.
 *
 * This composable creates a sophisticated screenshot animation combining multiple effects
 * to mimic the behavior of modern smartphones (iOS/Android) where the screenshot
 * preview animates to a specific UI element (typically a thumbnail in the corner or a button).
 * The effect includes a flash overlay, scale transformation, position offset, and fade out.
 *
 * @param image The captured screenshot image to display during the animation.
 * @param modifier The [Modifier] to apply to the container box.
 * @param alignment The alignment of the thumbnail within the container.
 * @param origin The transform origin point for scale transformations.
 * @param effect The [ScreenshotEffect] that controls the animations.
 * @param flash A composable lambda that renders the flash effect overlay.
 * @param shape The shape applied to the thumbnail image.
 *
 * @see ScreenshotEffect
 * @see ScreenshotEffectThumbnail
 * @see AlphaEffect
 * @see rememberScreenshotEffect
 */
@Composable
fun ScreenshotEffect(
    image: ImageBitmap,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    origin: TransformOrigin = TransformOrigin.Center,
    effect: ScreenshotEffect = rememberScreenshotEffect(),
    flash: @Composable BoxScope.(ScreenshotEffect) -> Unit = {
        AlphaEffect(effect = it.asFlashEffect())
    },
    shape: Shape = MaterialTheme.shapes.medium,
) {
    ScreenshotEffect(
        modifier = modifier,
        effect = effect,
        flash = flash,
        alignment = alignment,
    ) {
        ScreenshotEffectThumbnail(
            image = image,
            state = it,
            shape = shape,
            origin = origin,
        )
    }
}

/**
 * Displays a screenshot thumbnail with animated transformations.
 *
 * This composable renders the screenshot image with scale, offset, and alpha transformations
 * controlled by a [ScreenshotEffect]. The thumbnail is only visible when its alpha value is greater than 0.
 * It's typically used as part of a complete screenshot effect animation to show the animated preview.
 *
 * @param image The captured screenshot image to display.
 * @param modifier The [Modifier] to apply to the image.
 * @param state The [ScreenshotEffect] that controls the transformations.
 * @param origin The transform origin point for scale transformations.
 * @param shape The shape applied to the thumbnail image.
 *
 * @see ScreenshotEffect
 * @see rememberScreenshotEffect
 * @see Modifier.screenshotEffect
 */
@Composable
fun ScreenshotEffectThumbnail(
    image: ImageBitmap,
    modifier: Modifier = Modifier,
    state: ScreenshotEffect = rememberScreenshotEffect(),
    origin: TransformOrigin = TransformOrigin.Center,
    shape: Shape = MaterialTheme.shapes.medium,
) {
    val alpha by state.asHideContent()
    if (alpha > 0f) {
        Image(
            bitmap = image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier.fillMaxSize()
                .screenshotEffect(effect = state, shape = shape, origin = origin)
        )
    }
}
