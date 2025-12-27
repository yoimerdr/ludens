package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Arrow Right icon.
 *
 * This icon is a transformation of the filled `Chevron right` icon from Fluent Icons.
 */
val LudensIcons.Filled.ArrowRight: ImageVector
    get() {
        if (_ArrowRight != null) {
            return _ArrowRight!!
        }
        _ArrowRight = ImageVector.Builder(
            name = "Filled.ArrowRight",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(8.293f, 4.293f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 1.414f)
                lineTo(14.586f, 12f)
                lineToRelative(-6.293f, 6.293f)
                arcToRelative(
                    1f,
                    1f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    1.414f,
                    1.414f
                )
                lineToRelative(7f, -7f)
                arcToRelative(
                    1f,
                    1f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0f,
                    -1.414f
                )
                lineToRelative(-7f, -7f)
                arcToRelative(
                    1f,
                    1f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -1.414f,
                    0f
                )
                close()
            }
        }.build()

        return _ArrowRight!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowRight: ImageVector? = null
