package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Broad Activity Feed icon.
 *
 * This icon is a transformation of the outlined `Broad Activity Feed` icon from Fluent Icons.
 */
val LudensIcons.Outlined.BroadActivityFeed: ImageVector
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
                moveTo(9.255f, 14.005f)
                curveToRelative(0.966f, 0f, 1.75f, 0.783f, 1.75f, 1.75f)
                verticalLineToRelative(3.498f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.75f, 1.75f)
                lineTo(3.75f, 21.003f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 19.253f)
                verticalLineToRelative(-3.498f)
                curveToRelative(0f, -0.967f, 0.784f, -1.75f, 1.75f, -1.75f)
                horizontalLineToRelative(5.505f)
                close()
                moveTo(20.25f, 14.005f)
                curveToRelative(0.966f, 0f, 1.75f, 0.783f, 1.75f, 1.75f)
                verticalLineToRelative(3.498f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.75f, 1.75f)
                horizontalLineToRelative(-5.505f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.75f, -1.75f)
                verticalLineToRelative(-3.498f)
                curveToRelative(0f, -0.967f, 0.784f, -1.75f, 1.75f, -1.75f)
                horizontalLineToRelative(5.505f)
                close()
                moveTo(9.255f, 15.505f)
                lineTo(3.75f, 15.505f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.25f, 0.25f)
                verticalLineToRelative(3.498f)
                curveToRelative(0f, 0.139f, 0.112f, 0.25f, 0.25f, 0.25f)
                horizontalLineToRelative(5.505f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.25f, -0.25f)
                verticalLineToRelative(-3.498f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.25f, -0.25f)
                close()
                moveTo(20.25f, 15.505f)
                horizontalLineToRelative(-5.505f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.25f, 0.25f)
                verticalLineToRelative(3.498f)
                curveToRelative(0f, 0.139f, 0.112f, 0.25f, 0.25f, 0.25f)
                horizontalLineToRelative(5.505f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.25f, -0.25f)
                verticalLineToRelative(-3.498f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.25f, -0.25f)
                close()
                moveTo(20.25f, 3f)
                curveToRelative(0.966f, 0f, 1.75f, 0.784f, 1.75f, 1.75f)
                verticalLineToRelative(5.5f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 20.25f, 12f)
                lineTo(3.75f, 12f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 10.25f)
                verticalLineToRelative(-5.5f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.606f, -1.744f)
                lineTo(3.75f, 3f)
                horizontalLineToRelative(16.5f)
                close()
                moveTo(20.25f, 4.5f)
                lineTo(3.75f, 4.5f)
                lineToRelative(-0.057f, 0.007f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.193f, 0.243f)
                verticalLineToRelative(5.5f)
                curveToRelative(0f, 0.138f, 0.112f, 0.25f, 0.25f, 0.25f)
                horizontalLineToRelative(16.5f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.25f, -0.25f)
                verticalLineToRelative(-5.5f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.25f, -0.25f)
                close()
            }
        }.build()

        return _BroadActivityFeed!!
    }

@Suppress("ObjectPropertyName")
private var _BroadActivityFeed: ImageVector? = null
