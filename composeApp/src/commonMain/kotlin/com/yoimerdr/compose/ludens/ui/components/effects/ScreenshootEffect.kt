package com.yoimerdr.compose.ludens.ui.components.effects

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Visual screenshot effect that mimics modern device screenshot animations.
 *
 * This composable creates a sophisticated screenshot animation combining multiple effects,
 * and mimics the behavior of modern smartphones (iOS/Android) where the screenshot
 * preview animates to a specific UI element (typically a thumbnail in the corner or a button).
 *
 * @param trigger Boolean flag that triggers the animation when set to true. Set to false to reset.
 * @param image The captured screenshot image to display during the animation.
 * @param target The bounding rectangle (in root coordinates) of the target composable where the thumbnail should fly to.
 * If null, the thumbnail will fade out at the center without moving and use a default scale of 0.1.
 * @param shape The shape applied to the thumbnail image. Defaults to MaterialTheme.shapes.medium.
 * @param fraction The initial size fraction of the screen for the thumbnail. Defaults to 0.85 (85% of screen size).
 * @param borderColor The color of the border around the thumbnail. Defaults to MaterialTheme.colorScheme.outline.
 * @param borderWidth The width of the border around the thumbnail. Defaults to 2.dp.
 * @param elevation The shadow elevation applied to the thumbnail for depth effect. Defaults to 10.dp.
 * @param duration The total duration to wait before calling [onEffectComplete]. Defaults to 700ms.
 * @param onEffectComplete Callback invoked when the complete animation finishes and the effect is ready to be reset.
 */
@Composable
fun ScreenshotEffect(
    modifier: Modifier = Modifier,
    trigger: Boolean,
    image: ImageBitmap,
    target: Rect?,
    shape: Shape = MaterialTheme.shapes.medium,
    fraction: Float = 0.85f,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    borderWidth: Dp = 2f.dp,
    elevation: Dp = 10f.dp,
    duration: Long = 700,
    onEffectComplete: () -> Unit,
) {
    val flashAlpha = remember { Animatable(0f) }

    // Animation state variables for thumbnail transformation (scale, position, and opacity)
    val scale = remember { Animatable(1f) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val contentAlpha = remember { Animatable(0f) }

    // Container size needed to calculate center position and relative offsets
    var containerSize by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(trigger, target) {
        if (trigger) {
            // Step 1: Reset initial states to prepare for new animation
            scale.snapTo(1f)
            offsetX.snapTo(0f)
            offsetY.snapTo(0f)

            // Step 2: Flash Effect - Quick white flash to simulate camera capture
            flashAlpha.animateTo(0.8f, tween(100))  // Fast fade-in
            flashAlpha.animateTo(0f, tween(300))    // Slower fade-out

            contentAlpha.snapTo(1f)

            // Step 3: Calculate target position and scale
            // The thumbnail starts at the center of the container (0, 0 offset)
            // Calculate displacement from container center to target center
            val targetX = target?.center?.x?.minus(containerSize.width / 2f) ?: 0f
            val targetY = target?.center?.y?.minus(containerSize.height / 2f) ?: 0f

            // Calculate dynamic scale to fit exactly within the target width
            // This ensures the thumbnail scales to match the target size precisely
            val initialWidth = containerSize.width * fraction
            val targetScale = if (target != null && initialWidth > 0) {
                (target.width / initialWidth).coerceAtLeast(0.1f)
            } else {
                0.1f // Default fallback scale if no target is provided
            }

            // Step 4: Execute animations in parallel for smooth, synchronized movement
            val animationSpec: AnimationSpec<Float> = spring(
                dampingRatio = Spring.DampingRatioNoBouncy, // No bounce for precise targeting
                stiffness = Spring.StiffnessLow              // Low stiffness for smooth, slow movement
            )
            launch {
                // Animate all transformations simultaneously
                launch { scale.animateTo(targetScale, animationSpec) }
                launch { offsetX.animateTo(targetX, animationSpec) }
                launch { offsetY.animateTo(targetY, animationSpec) }
            }

            // Step 5: Final fade out - Delayed to keep thumbnail visible during flight
            launch {
                contentAlpha.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = 300,
                        delayMillis = 400 // Keep thumbnail visible while animating
                    )
                )
            }

            // Wait for total duration before signaling completion
            delay(duration)
            onEffectComplete()
        } else {
            // Reset animations when trigger is false (cleanup state)
            flashAlpha.snapTo(0f)
            contentAlpha.snapTo(0f)
        }
    }

    // Main effect container that fills the screen
    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { containerSize = it.size },
        contentAlignment = Alignment.Center // Critical: Thumbnail starts at center for correct offset calculations
    ) {
        // White Flash Overlay - Simulates camera flash
        if (flashAlpha.value > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = flashAlpha.value))
            )
        }

        // Screenshot Thumbnail - Animated preview that flies to target
        if (contentAlpha.value > 0f) {
            Image(
                bitmap = image,
                contentDescription = null,
                contentScale = ContentScale.Fit, // Fit maintains aspect ratio within bounds
                modifier = Modifier
                    .fillMaxSize(fraction) // Initial size as fraction of screen
                    .graphicsLayer {
                        // Apply scale transformation
                        this.scaleX = scale.value
                        this.scaleY = scale.value

                        // Apply position offset from center
                        this.translationX = offsetX.value
                        this.translationY = offsetY.value

                        // Apply opacity for fade effects
                        this.alpha = contentAlpha.value

                        // Ensure scaling happens from center to maintain alignment with translation
                        this.transformOrigin = TransformOrigin.Center

                        // Add shadow while flying for depth perception
                        this.shadowElevation = elevation.toPx()
                        this.shape = shape
                        this.clip = true
                    }
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = shape
                    )
            )
        }
    }
}

/**
 * Visual screenshot effect that mimics modern device screenshot animations using [Alignment].
 *
 * This is a convenience overload of [ScreenshotEffect] for use an [Alignment] and
 * It automatically calculates the target rectangle based on the provided
 * alignment and a target size.
 *
 * @param trigger Boolean flag that triggers the animation when set to true. Set to false to reset.
 * @param image The captured screenshot image to display during the animation.
 * @param alignment The alignment within the container where the thumbnail should fly to.
 * @param targetSize The size of the target area at the alignment position. Defaults to 0.dp.
 * @param shape The shape applied to the thumbnail image. Defaults to MaterialTheme.shapes.medium.
 * @param fraction The initial size fraction of the screen for the thumbnail. Defaults to 0.85 (85% of screen size).
 * @param borderColor The color of the border around the thumbnail. Defaults to MaterialTheme.colorScheme.outline.
 * @param borderWidth The width of the border around the thumbnail. Defaults to 2.dp.
 * @param elevation The shadow elevation applied to the thumbnail for depth effect. Defaults to 10.dp.
 * @param duration The total duration to wait before calling [onEffectComplete]. Defaults to 700ms.
 * @param onEffectComplete Callback invoked when the complete animation finishes and the effect is ready to be reset.
 */
@Composable
fun ScreenshotEffect(
    modifier: Modifier = Modifier,
    trigger: Boolean,
    image: ImageBitmap,
    alignment: Alignment,
    targetSize: Dp = 0f.dp,
    shape: Shape = MaterialTheme.shapes.medium,
    fraction: Float = 0.85f,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    borderWidth: Dp = 2f.dp,
    elevation: Dp = 10f.dp,
    duration: Long = 700,
    onEffectComplete: () -> Unit,
) {
    // Get density to convert Dp to pixels
    val density = LocalDensity.current

    var targetRect by remember { mutableStateOf<Rect?>(null) }
    var containerSize by remember { mutableStateOf(IntSize.Zero) }

    // Calculate target rect when container size changes or alignment changes
    LaunchedEffect(containerSize, alignment, targetSize) {
        if (containerSize != IntSize.Zero) {
            val targetSizePx = with(density) { targetSize.toPx() }

            // Convert alignment to position within container
            val position = alignment.align(
                size = IntSize(targetSizePx.toInt(), targetSizePx.toInt()),
                space = containerSize,
                layoutDirection = LayoutDirection.Ltr
            )

            // Create a rectangle centered at the alignment position
            val halfSize = targetSizePx / 2f
            targetRect = Rect(
                left = position.x.toFloat() - halfSize,
                top = position.y.toFloat() - halfSize,
                right = position.x.toFloat() + halfSize,
                bottom = position.y.toFloat() + halfSize
            )
        }
    }

    ScreenshotEffect(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                containerSize = coordinates.size
            },
        trigger = trigger,
        image = image,
        target = targetRect,
        shape = shape,
        fraction = fraction,
        borderColor = borderColor,
        borderWidth = borderWidth,
        elevation = elevation,
        duration = duration,
        onEffectComplete = onEffectComplete
    )
}
