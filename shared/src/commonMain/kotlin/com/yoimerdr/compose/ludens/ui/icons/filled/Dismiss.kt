package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Dismiss icon.
 *
 * This icon is a transformation of the filled `Dismiss` icon from Fluent Icons.
 */
val LudensIcons.Filled.Dismiss: ImageVector
    get() {
        if (_Dismiss != null) {
            return _Dismiss!!
        }
        _Dismiss = ImageVector.Builder(
            name = "Filled.Dismiss",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveToRelative(4.21f, 4.387f)
                lineToRelative(0.083f, -0.094f)
                arcToRelative(
                    1f,
                    1f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    1.32f,
                    -0.083f
                )
                lineToRelative(0.094f, 0.083f)
                lineTo(12f, 10.585f)
                lineToRelative(6.293f, -6.292f)
                arcToRelative(
                    1f,
                    1f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    1.414f,
                    1.414f
                )
                lineTo(13.415f, 12f)
                lineToRelative(6.292f, 6.293f)
                arcToRelative(
                    1f,
                    1f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.083f,
                    1.32f
                )
                lineToRelative(-0.083f, 0.094f)
                arcToRelative(
                    1f,
                    1f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -1.32f,
                    0.083f
                )
                lineToRelative(-0.094f, -0.083f)
                lineTo(12f, 13.415f)
                lineToRelative(-6.293f, 6.292f)
                arcToRelative(
                    1f,
                    1f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -1.414f,
                    -1.414f
                )
                lineTo(10.585f, 12f)
                lineTo(4.293f, 5.707f)
                arcToRelative(
                    1f,
                    1f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.083f,
                    -1.32f
                )
                lineToRelative(0.083f, -0.094f)
                lineToRelative(-0.083f, 0.094f)
                close()
            }
        }.build()

        return _Dismiss!!
    }

@Suppress("ObjectPropertyName")
private var _Dismiss: ImageVector? = null
