package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Arrow Counterclockwise icon.
 *
 * This icon is a transformation of the filled `Arrow Counterclockwise` icon from Fluent Icons.
 */
val LudensIcons.Filled.ArrowCounterclockwise: ImageVector
    get() {
        if (_ArrowCounterclockwise != null) {
            return _ArrowCounterclockwise!!
        }
        _ArrowCounterclockwise = ImageVector.Builder(
            name = "ArrowCounterclockwise",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(12f, 4.75f)
                arcToRelative(7.25f, 7.25f, 0f, isMoreThanHalf = true, isPositiveArc = true, -7.201f, 6.406f)
                curveTo(4.867f, 10.568f, 4.44f, 10f, 3.849f, 10f)
                curveToRelative(-0.515f, 0f, -0.968f, 0.358f, -1.03f, 0.87f)
                arcTo(9.25f, 9.25f, 0f, isMoreThanHalf = true, isPositiveArc = false, 6.25f, 4.754f)
                verticalLineTo(4.25f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2.001f, 0f)
                verticalLineToRelative(2.698f)
                arcTo(9.322f, 9.322f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.216f, 7f)
                horizontalLineToRelative(0.034f)
                verticalLineToRelative(0.25f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1f, 1f)
                horizontalLineToRelative(3f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -2f)
                horizontalLineToRelative(-0.666f)
                arcTo(7.219f, 7.219f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 4.75f)
                close()
            }
        }.build()

        return _ArrowCounterclockwise!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowCounterclockwise: ImageVector? = null
