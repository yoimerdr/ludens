package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * System icon.
 *
 * This icon is a transformation of the filled `Desktop` icon from Fluent Icons.
 */
val LudensIcons.Filled.System: ImageVector
    get() {
        if (_System != null) {
            return _System!!
        }
        _System = ImageVector.Builder(
            name = "System",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(4.946f, 5f)
                horizontalLineToRelative(14.108f)
                curveTo(20.678f, 5f, 22f, 6.304f, 22f, 7.92f)
                verticalLineToRelative(8.16f)
                curveToRelative(0f, 1.616f, -1.322f, 2.92f, -2.946f, 2.92f)
                lineTo(4.946f, 19f)
                curveTo(3.322f, 19f, 2f, 17.696f, 2f, 16.08f)
                lineTo(2f, 7.92f)
                curveTo(2f, 6.304f, 3.322f, 5f, 4.946f, 5f)
                close()
                moveTo(4.946f, 7f)
                arcTo(0.933f, 0.933f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4f, 7.92f)
                verticalLineToRelative(8.16f)
                curveToRelative(0f, 0.505f, 0.42f, 0.92f, 0.946f, 0.92f)
                horizontalLineToRelative(14.108f)
                arcToRelative(0.933f, 0.933f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.946f, -0.92f)
                lineTo(20f, 7.92f)
                curveToRelative(0f, -0.505f, -0.42f, -0.92f, -0.946f, -0.92f)
                lineTo(4.946f, 7f)
                close()
            }
        }.build()

        return _System!!
    }

@Suppress("ObjectPropertyName")
private var _System: ImageVector? = null
