package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Circle icon.
 *
 * This icon is a transformation of the outlined `Circle` icon from Fluent Icons.
 */
val LudensIcons.Outlined.Circle: ImageVector
    get() {
        if (_Circle != null) {
            return _Circle!!
        }
        _Circle = ImageVector.Builder(
            name = "Circle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(12f, 3.5f)
                arcToRelative(8.5f, 8.5f, 0f, isMoreThanHalf = true, isPositiveArc = false, 0f, 17f)
                arcToRelative(8.5f, 8.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -17f)
                close()
                moveTo(2f, 12f)
                curveTo(2f, 6.477f, 6.477f, 2f, 12f, 2f)
                reflectiveCurveToRelative(10f, 4.477f, 10f, 10f)
                reflectiveCurveToRelative(-4.477f, 10f, -10f, 10f)
                reflectiveCurveTo(2f, 17.523f, 2f, 12f)
                close()
            }
        }.build()

        return _Circle!!
    }

@Suppress("ObjectPropertyName")
private var _Circle: ImageVector? = null
