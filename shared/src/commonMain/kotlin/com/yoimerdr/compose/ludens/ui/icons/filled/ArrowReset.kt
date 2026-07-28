package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Arrow Reset icon.
 *
 * This icon is a transformation of the filled `Arrow Reset` icon from Fluent Icons.
 */
val LudensIcons.Filled.ArrowReset: ImageVector
    get() {
        if (_ArrowReset != null) {
            return _ArrowReset!!
        }
        _ArrowReset = ImageVector.Builder(
            name = "ArrowReset",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(7.207f, 2.543f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 1.414f)
                lineTo(5.414f, 5.75f)
                horizontalLineToRelative(7.836f)
                arcToRelative(8f, 8f, 0f, isMoreThanHalf = true, isPositiveArc = true, -8f, 8f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = true, 2f, 0f)
                arcToRelative(6f, 6f, 0f, isMoreThanHalf = true, isPositiveArc = false, 6f, -6f)
                horizontalLineTo(5.414f)
                lineToRelative(1.793f, 1.793f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.414f, 1.414f)
                lineToRelative(-3.5f, -3.5f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -1.414f)
                lineToRelative(3.5f, -3.5f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.414f, 0f)
                close()
            }
        }.build()

        return _ArrowReset!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowReset: ImageVector? = null
