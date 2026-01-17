package com.yoimerdr.compose.ludens.ui.state

/**
 * Represents the four edges of a rectangular area.
 */
enum class Edge {
    /** The top edge. */
    Top,

    /** The bottom edge. */
    Bottom,

    /** The left edge. */
    Left,

    /** The right edge. */
    Right
}

/**
 * Returns the opposite value for this edge.
 * * Top -> Bottom
 * * Bottom -> Top
 * * Left -> Right
 * * Right -> Left
 */
val Edge.reverse: Edge
    get() = when (this) {
        Edge.Top -> Edge.Bottom
        Edge.Bottom -> Edge.Top
        Edge.Left -> Edge.Right
        Edge.Right -> Edge.Left
    }

/**
 * Whether this edge is horizontal (Left or Right).
 */
val Edge.isHorizontal: Boolean
    get() = this == Edge.Left || this == Edge.Right

/**
 * Whether this edge is vertical (Top or Bottom).
 */
val Edge.isVertical: Boolean
    get() = this == Edge.Top || this == Edge.Bottom