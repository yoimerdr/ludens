package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Arrow Right icon.
 *
 * This icon is a transformation of the outlined `Chevron right` icon from Fluent Icons.
 */
val LudensIcons.Outlined.ArrowRight: ImageVector
    get() {
        if (_ArrowRight != null) {
            return _ArrowRight!!
        }
        _ArrowRight =
            ImageVector.Builder(
                name = "Outlined.ArrowRight",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).apply {
                path(fill = SolidColor(Color.White)) {
                    moveTo(8.47f, 4.22f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        0f,
                        1.06f
                    )
                    lineTo(15.19f, 12f)
                    lineToRelative(-6.72f, 6.72f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = true,
                        isPositiveArc = false,
                        1.06f,
                        1.06f
                    )
                    lineToRelative(7.25f, -7.25f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        0f,
                        -1.06f
                    )
                    lineTo(9.53f, 4.22f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        -1.06f,
                        0f
                    )
                    close()
                }
            }.build()

        return _ArrowRight!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowRight: ImageVector? = null
