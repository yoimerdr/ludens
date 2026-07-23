package com.yoimerdr.compose.ludens.ui.state.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.yoimerdr.compose.ludens.ui.state.Edge
import kotlin.math.min

/**
 * Defines the strategy for determining which edge the floating content should open to.
 */
enum class FloatingStrategy {
    /** Opens the content to the top. */
    Top,

    /** Opens the content to the bottom. */
    Bottom,

    /** Opens the content to the left. */
    Left,

    /** Opens the content to the right. */
    Right,

    /**
     * Opens the content vertically (up or down) based on available space.
     * Opens downward if more space is available below, otherwise opens upward.
     */
    Vertical,

    /**
     * Opens the content horizontally (left or right) based on available space.
     *
     * Opens to the right if more space is available on the right, otherwise opens to the left.
     */
    Horizontal,

    /**
     * Automatically determines the best direction based on all available space.
     *
     * Compares horizontal and vertical space, then chooses the direction with the most room.
     */
    Auto
}

/**
 * Represents the state of a floating content that can open to different edges.
 *
 * This state manages the content's open/closed status and determines which edge
 * the content should appear.
 */
interface FloatingState {
    /**
     * The edge to which the content will appear.
     *
     * This determines the direction the content opens.
     */
    val edge: Edge

    /**
     * Whether the content is currently open.
     */
    val isOpen: Boolean

    /**
     * Sets which edge the content should open to.
     *
     * @param edge The edge to open the content to.
     */
    fun setOpenEdge(edge: Edge)

    /**
     * Opens the content.
     */
    fun open()

    /**
     * Closes the content.
     */
    fun close()

    /**
     * Toggles the content's open state.
     *
     * Opens the content if closed, closes it if open.
     */
    fun toggle()
}

/**
 * Converts an [Edge] to its corresponding [FloatingStrategy].
 *
 * The strategy is the same as the edge (e.g., if the value is Top edge,
 * the strategy is Top to show content upward).
 */

fun Edge.toFloatingStrategy(): FloatingStrategy {
    return when (this) {
        Edge.Top -> FloatingStrategy.Top
        Edge.Bottom -> FloatingStrategy.Bottom
        Edge.Left -> FloatingStrategy.Left
        Edge.Right -> FloatingStrategy.Right
    }
}

/**
 * Determines and sets the optimal edge for the content to open from based on the specified strategy.
 *
 * For [FloatingStrategy.Horizontal], [FloatingStrategy.Vertical] and [FloatingStrategy.Auto]
 * this function calculates available space in all directions from the center position
 * and applies the strategy to determine which edge provides the best placement.
 *
 * @param strategy The strategy to use for determining the opening edge.
 * @param center The center position of the opener in the parent coordinate space.
 * @param size The size of the parent container.
 *
 */

fun FloatingState.setOpenEdge(
    strategy: FloatingStrategy,
    center: Offset,
    size: Size,
) {
    val start = center.x
    val end = size.width - center.x
    val top = center.y
    val bottom = size.height - center.y

    val side = when (strategy) {
        FloatingStrategy.Top -> Edge.Top
        FloatingStrategy.Bottom -> Edge.Bottom
        FloatingStrategy.Left -> Edge.Left
        FloatingStrategy.Right -> Edge.Right

        FloatingStrategy.Vertical -> {
            if (top < bottom) Edge.Bottom else Edge.Top
        }

        FloatingStrategy.Horizontal -> {
            if (start < end) Edge.Right else Edge.Left
        }

        FloatingStrategy.Auto -> {
            val horizontal = min(start, end)
            val vertical = min(top, bottom)

            if (horizontal < vertical) {
                if (start < end) Edge.Right else Edge.Left
            } else {
                if (top < bottom) Edge.Bottom else Edge.Top
            }
        }
    }

    this.setOpenEdge(side)
}


/**
 * Default implementation of [FloatingState].
 *
 * This class provides a mutable state implementation for managing a floating content's
 * open/closed status and the edge to which it opens.
 *
 * @param initialEdge The initial edge to which the content will open.
 * @param initialOpen The initial open state of the content.
 */
@Stable
class DefaultFloatingState(
    initialEdge: Edge = Edge.Left,
    initialOpen: Boolean = false,
) : FloatingState {
    override var edge by mutableStateOf(initialEdge)
        private set

    override var isOpen by mutableStateOf(initialOpen)
        private set


    override fun setOpenEdge(edge: Edge) {
        this.edge = edge
    }

    override fun open() {
        isOpen = true
    }

    override fun close() {
        isOpen = false
    }

    override fun toggle() {
        this.isOpen = !this.isOpen
    }
}

/**
 * Creates and remembers a [FloatingState] for managing a floating content.
 *
 * @param edge The initial edge to which the content will open.
 * @param isOpen The initial open state of the content.
 */
@Composable
fun rememberFloatingState(
    edge: Edge = Edge.Right,
    isOpen: Boolean = false,
): FloatingState = remember(edge, isOpen) { DefaultFloatingState(edge, isOpen) }
