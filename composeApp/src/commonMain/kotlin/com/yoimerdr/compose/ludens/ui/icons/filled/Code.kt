package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Code icon.
 *
 * This icon is a transformation of the filled `Code` icon from Fluent Icons.
 */
val LudensIcons.Filled.Code: ImageVector
    get() {
        if (_Code != null) {
            return _Code!!
        }
        _Code = ImageVector.Builder(
            name = "Code",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveToRelative(8.086f, 18.611f)
                lineToRelative(5.996f, -14.004f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.878f, 0.677f)
                lineToRelative(-0.04f, 0.11f)
                lineToRelative(-5.996f, 14.004f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.878f, -0.677f)
                lineToRelative(0.04f, -0.11f)
                lineToRelative(5.996f, -14.004f)
                lineTo(8.086f, 18.61f)
                close()
                moveTo(2.293f, 11.293f)
                lineTo(6.293f, 7.293f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.497f, 1.32f)
                lineToRelative(-0.083f, 0.094f)
                lineTo(4.414f, 12f)
                lineToRelative(3.293f, 3.293f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.32f, 1.498f)
                lineToRelative(-0.094f, -0.084f)
                lineToRelative(-4f, -4f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.083f, -1.32f)
                lineToRelative(0.083f, -0.094f)
                lineToRelative(4f, -4f)
                lineToRelative(-4f, 4f)
                close()
                moveTo(16.293f, 7.292f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.32f, -0.083f)
                lineToRelative(0.093f, 0.083f)
                lineToRelative(4.001f, 4.001f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.083f, 1.32f)
                lineToRelative(-0.083f, 0.095f)
                lineToRelative(-4.001f, 3.995f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.497f, -1.32f)
                lineToRelative(0.084f, -0.095f)
                lineTo(19.584f, 12f)
                lineToRelative(-3.293f, -3.294f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -1.414f)
                close()
            }
        }.build()

        return _Code!!
    }

@Suppress("ObjectPropertyName")
private var _Code: ImageVector? = null
