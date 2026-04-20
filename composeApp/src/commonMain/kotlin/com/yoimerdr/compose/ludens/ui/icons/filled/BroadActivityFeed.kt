package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Broad Activity Feed icon.
 *
 * This icon is a transformation of the filled `Broad Activity Feed` icon from Fluent Icons.
 */
val LudensIcons.Filled.BroadActivityFeed: ImageVector
    get() {
        if (_BroadActivityFeed != null) {
            return _BroadActivityFeed!!
        }
        _BroadActivityFeed = ImageVector.Builder(
            name = "BroadActivityFeed",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(9.246f, 14.001f)
                curveToRelative(0.967f, 0f, 1.75f, 0.784f, 1.75f, 1.75f)
                verticalLineToRelative(3.5f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.75f, 1.75f)
                lineTo(3.75f, 21.001f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 19.251f)
                verticalLineToRelative(-3.5f)
                curveToRelative(0f, -0.966f, 0.784f, -1.75f, 1.75f, -1.75f)
                horizontalLineToRelative(5.496f)
                close()
                moveTo(20.25f, 14.001f)
                curveToRelative(0.966f, 0f, 1.75f, 0.784f, 1.75f, 1.75f)
                verticalLineToRelative(3.5f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.75f, 1.75f)
                horizontalLineToRelative(-5.496f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.75f, -1.75f)
                verticalLineToRelative(-3.5f)
                curveToRelative(0f, -0.966f, 0.783f, -1.75f, 1.75f, -1.75f)
                horizontalLineToRelative(5.496f)
                close()
                moveTo(20.25f, 2.996f)
                curveToRelative(0.966f, 0f, 1.75f, 0.784f, 1.75f, 1.75f)
                verticalLineToRelative(5.503f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 20.25f, 12f)
                lineTo(3.75f, 12f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 10.25f)
                lineTo(2f, 4.746f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.606f, -1.744f)
                lineToRelative(0.144f, -0.006f)
                horizontalLineToRelative(16.5f)
                close()
            }
        }.build()

        return _BroadActivityFeed!!
    }

@Suppress("ObjectPropertyName")
private var _BroadActivityFeed: ImageVector? = null
