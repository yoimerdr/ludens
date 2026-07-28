package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Arrow Left icon.
 *
 * This icon is a transformation of the outlined `Chevron Left` icon from Fluent Icons.
 */
val LudensIcons.Outlined.ArrowLeft: ImageVector
    get() {
        if (_ArrowLeft != null) {
            return _ArrowLeft!!
        }
        _ArrowLeft =
            ImageVector.Builder(
                name = "Outlined.ArrowLeft",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).apply {
                path(fill = SolidColor(Color.White)) {
                    moveTo(15.53f, 4.22f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        0f,
                        1.06f
                    )
                    lineTo(8.81f, 12f)
                    lineToRelative(6.72f, 6.72f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = true,
                        isPositiveArc = true,
                        -1.06f,
                        1.06f
                    )
                    lineToRelative(-7.25f, -7.25f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        0f,
                        -1.06f
                    )
                    lineToRelative(7.25f, -7.25f)
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

        return _ArrowLeft!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowLeft: ImageVector? = null

