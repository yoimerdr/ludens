package com.yoimerdr.compose.ludens.ui.components.drag

import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.round
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.Drag
import com.yoimerdr.compose.ludens.ui.state.drag.ClampedDraggableState
import com.yoimerdr.compose.ludens.ui.state.drag.rememberClampedDraggableState


/**
 * Applies clamped draggable behavior to a component.
 *
 * @param state The state that manages the drag behavior.
 * @param enabled Whether dragging is enabled.
 * @param interactionSource The interaction source to use for drag events.
 * @param startDragImmediately Whether to start dragging immediately.
 * @param reverseDirection Whether to reverse the direction of the drag.
 *
 * @see draggable2D
 */
@Composable
fun Modifier.clampedDraggable(
    state: ClampedDraggableState,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    startDragImmediately: Boolean = false,
    reverseDirection: Boolean = false,
): Modifier {
    val draggable = rememberDraggable2DState(
        onDelta = state::dragBy
    )
    return then(
        if (enabled) {
            Modifier.draggable2D(
                state = draggable,
                onDragStarted = {
                    state.onStart(state.offset)
                },
                onDragStopped = {
                    state.onEnd(state.offset)
                },
                interactionSource = interactionSource,
                startDragImmediately = startDragImmediately,
                reverseDirection = reverseDirection
            )
        } else Modifier
    )
}

/**
 * Configures bounds and offset for a draggable component.
 *
 * @param state The state that will receive the calculated bounds and provide the offset.
 * @param enabled Whether dragging is enabled.
 */
fun Modifier.clampedDraggableSource(
    state: ClampedDraggableState,
    enabled: Boolean = true,
): Modifier = then(
    if (enabled)
        Modifier.onPlaced {
            val parent = it.parentLayoutCoordinates ?: it.findRootCoordinates()
            val parentBounds = parent.boundsInParent()
            val placedBounds = parent.localBoundingBoxOf(it)

            state.bounds = Rect(
                left = -placedBounds.left,
                top = -placedBounds.top,
                right = parentBounds.width - placedBounds.right,
                bottom = parentBounds.height - placedBounds.bottom
            )
        }
    else Modifier
)


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
 * @param icon A custom composable for the drag icon.
 * @param iconAlignment The alignment of the drag icon.
 * @param position The position of the drag icon. See [DraggablePosition].
 * @param state Optional external state to manage dragging. If null, internal state is used.
 * @param onOffsetChange A callback that is invoked when the offset of the component changes.
 * @param onDragEnd A callback that is invoked when the drag gesture ends.
 * @param offset The initial offset of the component (only used if state is null).
 * @param content The content of the draggable component.
 */
@Composable
fun Draggable(
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    mode: DraggableMode = DraggableMode.IconOnly,
    icon: (@Composable () -> Unit) = ::DraggableIcon,
    iconAlignment: Alignment = Alignment.TopStart,
    position: DraggablePosition = DraggablePosition.Inside,
    onDragStart: ((Offset) -> Unit)? = null,
    onOffsetChange: ((Offset) -> Unit)? = null,
    onDragEnd: ((Offset) -> Unit)? = null,
    offset: Offset = Offset.Zero,
    state: ClampedDraggableState = rememberClampedDraggableState(
        initialOffset = offset, onChange = onOffsetChange, onEnd = onDragEnd, onStart = onDragStart
    ),
    content: @Composable BoxScope.() -> Unit,
) {

    val iconDragger = if (enable) {
        Modifier.clampedDraggable(
            state = state, enabled = true
        )
    } else Modifier

    val contentDragger = if (enable && mode == DraggableMode.IconAndContent) {
        iconDragger
    } else Modifier

    Box(
        modifier = modifier.clampedDraggableSource(state, enable)
            .offset {
                if (enable)
                    state.offset.round()
                else IntOffset.Zero
            }
    ) {
        if (enable && position != DraggablePosition.None) {
            // Use Layout to make the icon floating (not affecting parent size)
            DraggableLayout(
                modifier = contentDragger,
                iconAlignment = iconAlignment,
                position = position,
                icon = {
                    Box(
                        modifier = iconDragger
                    ) {
                        icon()
                    }
                }) {
                Box(content = content)
            }
        } else {
            Box(
                content = content, modifier = contentDragger
            )
        }
    }
}

/**
 * A custom layout that positions a drag icon relative to content.
 *
 * This layout composes an icon and content, where the icon can be positioned either inside
 * or outside the content boundaries. When positioned outside, the layout's width expands to
 * accommodate the icon. When positioned inside, the icon overlays the content without affecting
 * the layout's size.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param position The position of the drag icon relative to the content. See [DraggablePosition].
 * @param iconAlignment The alignment of the drag icon within its designated area.
 * @param icon A composable for the drag icon.
 * @param content The main content to be displayed.
 */
@Composable
fun DraggableLayout(
    modifier: Modifier = Modifier,
    position: DraggablePosition,
    iconAlignment: Alignment = Alignment.TopStart,
    icon: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier, content = {
            content()
            icon()
        }) { measurables, constraints ->
        // Measure content
        val contentPlaceable = measurables[0].measure(constraints)

        // Measure icon with relaxed constraints
        val iconPlaceable = measurables[1].measure(constraints.copy(minWidth = 0, minHeight = 0))

        // Layout size is determined only by content
        val width = when (position) {
            DraggablePosition.Outside -> contentPlaceable.width + iconPlaceable.width
            else -> contentPlaceable.width
        }
        val height = contentPlaceable.height

        layout(width, height) {
            // Place content
            val contentX = when (position) {
                DraggablePosition.Outside -> iconPlaceable.width
                else -> 0
            }
            contentPlaceable.place(contentX, 0)

            if (position != DraggablePosition.None) {
                // Place icon (floating)
                val (iconX, iconY) = when (iconAlignment) {
                    Alignment.TopStart -> when (position) {
                        DraggablePosition.Outside -> 0 to 0
                        else -> 0 to 0
                    }

                    Alignment.TopEnd -> when (position) {
                        DraggablePosition.Outside -> width to 0
                        else -> (contentPlaceable.width - iconPlaceable.width) to 0
                    }

                    Alignment.BottomStart -> when (position) {
                        DraggablePosition.Outside -> 0 to (height - iconPlaceable.height)
                        else -> 0 to (height - iconPlaceable.height)
                    }

                    Alignment.BottomEnd -> when (position) {
                        DraggablePosition.Outside -> width to (height - iconPlaceable.height)
                        else -> (contentPlaceable.width - iconPlaceable.width) to (height - iconPlaceable.height)
                    }

                    else -> when (position) {
                        DraggablePosition.Outside -> 0 to 0
                        else -> 0 to 0
                    }
                }

                iconPlaceable.place(iconX, iconY)
            }
        }
    }
}