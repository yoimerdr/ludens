package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Code icon.
 *
 * This icon is a transformation of the outlined `Code` icon from Fluent Icons.
 */
val LudensIcons.Outlined.Code: ImageVector
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
                moveToRelative(8.066f, 18.943f)
                lineToRelative(6.5f, -14.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.404f, 0.518f)
                lineToRelative(-0.036f, 0.096f)
                lineToRelative(-6.5f, 14.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.404f, -0.518f)
                lineToRelative(0.036f, -0.096f)
                lineToRelative(6.5f, -14.5f)
                lineToRelative(-6.5f, 14.5f)
                close()
                moveTo(2.22f, 11.47f)
                lineToRelative(4.25f, -4.25f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.133f, 0.976f)
                lineToRelative(-0.073f, 0.085f)
                lineTo(3.81f, 12f)
                lineToRelative(3.72f, 3.719f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.976f, 1.133f)
                lineToRelative(-0.084f, -0.073f)
                lineToRelative(-4.25f, -4.25f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.073f, -0.976f)
                lineToRelative(0.073f, -0.084f)
                lineToRelative(4.25f, -4.25f)
                lineToRelative(-4.25f, 4.25f)
                close()
                moveTo(16.47f, 7.22f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.976f, -0.073f)
                lineToRelative(0.084f, 0.073f)
                lineToRelative(4.25f, 4.25f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.073f, 0.976f)
                lineToRelative(-0.073f, 0.085f)
                lineToRelative(-4.25f, 4.25f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.133f, -0.977f)
                lineToRelative(0.073f, -0.084f)
                lineTo(20.19f, 12f)
                lineToRelative(-3.72f, -3.72f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -1.06f)
                close()
            }
        }.build()

        return _Code!!
    }

@Suppress("ObjectPropertyName")
private var _Code: ImageVector? = null
