package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Arrow Counterclockwise icon.
 *
 * This icon is a transformation of the outlined `Arrow Counterclockwise` icon from Fluent Icons.
 */
val LudensIcons.Outlined.ArrowCounterclockwise: ImageVector
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
                moveTo(12f, 4.5f)
                arcToRelative(7.5f, 7.5f, 0f, isMoreThanHalf = true, isPositiveArc = true, -7.419f, 6.392f)
                curveToRelative(0.067f, -0.454f, -0.265f, -0.892f, -0.724f, -0.892f)
                arcToRelative(0.749f, 0.749f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.752f, 0.623f)
                arcTo(9f, 9f, 0f, isMoreThanHalf = true, isPositiveArc = false, 6f, 5.292f)
                verticalLineTo(4.25f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.5f, 0f)
                verticalLineToRelative(3f)
                curveToRelative(0f, 0.414f, 0.336f, 0.75f, 0.75f, 0.75f)
                horizontalLineToRelative(3f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -1.5f)
                horizontalLineTo(6.9f)
                arcToRelative(7.473f, 7.473f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5.1f, -2f)
                close()
            }
        }.build()

        return _ArrowCounterclockwise!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowCounterclockwise: ImageVector? = null
