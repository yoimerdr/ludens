package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Arrow Reset icon.
 *
 * This icon is a transformation of the outlined `Arrow Reset` icon from Fluent Icons.
 */
val LudensIcons.Outlined.ArrowReset: ImageVector
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
                moveTo(6.78f, 2.72f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0f,
                    1.06f
                )
                lineTo(4.56f, 6f)
                horizontalLineToRelative(8.69f)
                arcToRelative(
                    7.75f,
                    7.75f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    -7.75f,
                    7.75f
                )
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    1.5f,
                    0f
                )
                arcToRelative(
                    6.25f,
                    6.25f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    6.25f,
                    -6.25f
                )
                horizontalLineTo(4.56f)
                lineToRelative(2.22f, 2.22f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    -1.06f,
                    1.06f
                )
                lineToRelative(-3.5f, -3.5f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0f,
                    -1.06f
                )
                lineToRelative(3.5f, -3.5f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    1.06f,
                    0f
                )
                close()
            }
        }.build()

        return _ArrowReset!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowReset: ImageVector? = null
