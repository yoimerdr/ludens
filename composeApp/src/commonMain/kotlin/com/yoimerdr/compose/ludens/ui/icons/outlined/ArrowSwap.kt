package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Arrow Swap icon.
 *
 * This icon is a transformation of the outlined `Arrow Swap` icon from Fluent Icons.
 */
val LudensIcons.Outlined.ArrowSwap: ImageVector
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
                moveToRelative(14.783f, 2.22f)
                lineToRelative(4.495f, 4.494f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.073f, 0.976f)
                lineToRelative(-0.072f, 0.085f)
                lineToRelative(-4.495f, 4.504f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.135f, -0.975f)
                lineToRelative(0.073f, -0.084f)
                lineToRelative(3.217f, -3.223f)
                horizontalLineTo(5.243f)
                arcTo(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.5f, 7.35f)
                lineToRelative(-0.007f, -0.101f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.648f, -0.743f)
                lineToRelative(0.102f, -0.007f)
                lineToRelative(11.697f, -0.001f)
                lineToRelative(-3.218f, -3.217f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.072f, -0.976f)
                lineToRelative(0.072f, -0.084f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.977f, -0.073f)
                lineToRelative(0.084f, 0.073f)
                lineToRelative(4.495f, 4.494f)
                lineToRelative(-4.495f, -4.494f)
                close()
                moveTo(19.5f, 16.65f)
                lineToRelative(0.006f, 0.1f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.648f, 0.744f)
                lineToRelative(-0.102f, 0.007f)
                lineTo(7.063f, 17.5f)
                lineToRelative(3.22f, 3.22f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.074f, 0.976f)
                lineToRelative(-0.073f, 0.084f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.976f, 0.073f)
                lineToRelative(-0.085f, -0.072f)
                lineToRelative(-4.5f, -4.497f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.073f, -0.976f)
                lineToRelative(0.073f, -0.084f)
                lineToRelative(4.5f, -4.504f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.134f, 0.976f)
                lineToRelative(-0.073f, 0.084f)
                lineTo(7.066f, 16f)
                horizontalLineToRelative(11.692f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.743f, 0.65f)
                lineToRelative(0.006f, 0.1f)
                lineToRelative(-0.006f, -0.1f)
                close()
            }
        }.build()

        return _ArrowSwap!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowSwap: ImageVector? = null
