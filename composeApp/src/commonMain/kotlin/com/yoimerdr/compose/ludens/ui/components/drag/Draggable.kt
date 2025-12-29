package com.yoimerdr.compose.ludens.ui.components.drag

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.Drag
import kotlin.math.roundToInt

/**
 * Defines the position of the drag icon relative to the content.
 */
enum class DraggablePosition {
    /**
     * The drag icon is positioned outside the content.
     */
    Outside,
    /**
     * The drag icon is positioned inside the content.
     */
    Inside,
    /**
     * No drag icon is shown.
     */
    None
}

/**
 * Defines the drag behavior of the component.
 */
enum class DraggableMode {
    /**
     * Only the drag icon can be used to drag the component.
     */
    IconOnly,
    /**
     * Both the drag icon and the content can be used to drag the component.
     */
    IconAndContent
}

/**
 * A default composable for the drag icon.
 * @param modifier The modifier to be applied to the icon.
 */
@Composable
fun DraggableIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        LudensIcons.Default.Drag,
        contentDescription = "Drag Icon",
        modifier = modifier,
        tint = Color.White
    )
}

/**
 * A composable that allows its content to be dragged.
 *
 * @param modifier The modifier to be applied to the component.
 * @param enable Whether dragging is enabled.
 * @param mode The drag mode. See [DraggableMode].
 * @param icon A custom composable for the drag icon. If null, a default icon is used.
 * @param iconAlignment The alignment of the drag icon.
 * @param position The position of the drag icon. See [DraggablePosition].
 * @param onOffsetChange A callback that is invoked when the offset of the component changes.
 * @param onDragEnd A callback that is invoked when the drag gesture ends.
 * @param offset The initial offset of the component.
 * @param content The content of the draggable component.
 */
@Composable
fun Draggable(
    modifier: Modifier = Modifier,
    enable: Boolean = false,
    mode: DraggableMode = DraggableMode.IconOnly,
    icon: (@Composable () -> Unit)? = null,
    iconAlignment: Alignment = Alignment.TopStart,
    position: DraggablePosition = DraggablePosition.Outside,
    onOffsetChange: ((Offset) -> Unit)? = null,
    onDragEnd: ((Offset) -> Unit)? = null,
    offset: Offset = Offset.Zero,
    content: @Composable BoxScope.() -> Unit,
) {
    val density = LocalDensity.current
    var iconSize by remember { mutableStateOf(0.dp) }
    var offset by remember { mutableStateOf(offset) }


    var size = remember { Size.Zero }
    var initialPosition = remember { Offset.Zero }

    val dragger = remember(onDragEnd, onOffsetChange) {
        Modifier.pointerInput(Unit) {
            detectDragGestures(
                onDragEnd = {
                    onDragEnd?.invoke(offset)
                },
                onDragCancel = {
                    onDragEnd?.invoke(offset)
                }
            ) { _, amount ->
                offset += amount
                offset = Offset(
                    offset.x.coerceIn(
                        -initialPosition.x,
                        (size.width - initialPosition.x)
                            .coerceAtLeast(-initialPosition.x)
                    ),
                    offset.y.coerceIn(
                        -initialPosition.y,
                        (size.height - initialPosition.y)
                            .coerceAtLeast(-initialPosition.y)
                    )
                )

                onOffsetChange?.invoke(offset)
            }
        }
    }

    Box(
        modifier = modifier
            .then(
                if (enable)
                    Modifier.onPlaced {
                        initialPosition = it.positionInParent()
                    }.offset {
                        IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
                    }.layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        val childSize = IntSize(placeable.width, placeable.height)

                        val maxWidth = (constraints.maxWidth - childSize.width)
                            .toFloat()
                            .coerceAtLeast(0f)

                        val maxHeight = (constraints.maxHeight - childSize.height)
                            .toFloat()
                            .coerceAtLeast(0f)

                        size = Size(maxWidth, maxHeight)

                        layout(placeable.width, placeable.height) {
                            placeable.place(0, 0)
                        }
                    }
                else Modifier
            )
    ) {
        if (position == DraggablePosition.Inside || !enable) {
            Box(content = content)
        }

        if (enable) {
            if (position != DraggablePosition.None) {
                Box(
                    modifier = Modifier
                        .align(iconAlignment)
                        .then(
                            when (position) {
                                DraggablePosition.Outside -> Modifier.onPlaced {
                                    val width = it.size.width
                                    iconSize = with(density) { width.toDp() }
                                }

                                else -> Modifier
                            }
                        ).then(dragger),
                ) {
                    if (icon == null)
                        DraggableIcon()
                    else icon()
                }
            }

            if (position in setOf(
                    DraggablePosition.Outside,
                    DraggablePosition.None
                )
            ) {
                Box(
                    modifier = Modifier.padding(iconSize)
                ) {
                    Box(
                        content = content,
                        modifier = when (mode) {
                            DraggableMode.IconAndContent -> dragger
                            DraggableMode.IconOnly -> Modifier
                        }
                    )
                }
            }
        }
    }
}