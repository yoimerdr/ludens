package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Arrow Swap icon.
 *
 * This icon is a transformation of the filled `Arrow Swap` icon from Fluent Icons.
 */
val LudensIcons.Filled.ArrowSwap: ImageVector
    get() {
        if (_ArrowSwap != null) {
            return _ArrowSwap!!
        }
        _ArrowSwap = ImageVector.Builder(
            name = "ArrowSwap",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveToRelative(15.207f, 2.292f)
                lineToRelative(4f, 3.995f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.084f, 1.32f)
                lineToRelative(-0.083f, 0.094f)
                lineToRelative(-4f, 4.006f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.498f, -1.32f)
                lineToRelative(0.083f, -0.094f)
                lineTo(16.083f, 8f)
                lineTo(5.5f, 8f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.994f, -0.883f)
                lineTo(4.5f, 7f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.883f, -0.993f)
                lineTo(5.5f, 6f)
                horizontalLineToRelative(10.59f)
                lineToRelative(-2.296f, -2.293f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.084f, -1.32f)
                lineToRelative(0.083f, -0.095f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.32f, -0.084f)
                lineToRelative(0.094f, 0.084f)
                lineToRelative(4f, 3.995f)
                lineToRelative(-4f, -3.995f)
                close()
                moveTo(19.49f, 16.883f)
                lineTo(19.497f, 17f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.883f, 0.993f)
                lineToRelative(-0.117f, 0.007f)
                lineTo(7.913f, 18f)
                lineToRelative(2.294f, 2.293f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.084f, 1.32f)
                lineToRelative(-0.083f, 0.094f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.32f, 0.084f)
                lineToRelative(-0.095f, -0.084f)
                lineToRelative(-4f, -3.996f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.083f, -1.32f)
                lineToRelative(0.083f, -0.094f)
                lineToRelative(4f, -4.004f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.498f, 1.32f)
                lineToRelative(-0.083f, 0.094f)
                lineTo(7.918f, 16f)
                horizontalLineToRelative(10.579f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.993f, 0.883f)
                lineToRelative(0.007f, 0.117f)
                lineToRelative(-0.007f, -0.117f)
                close()
            }
        }.build()

        return _ArrowSwap!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowSwap: ImageVector? = null
