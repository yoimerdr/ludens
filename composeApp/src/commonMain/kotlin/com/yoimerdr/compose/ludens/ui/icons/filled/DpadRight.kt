package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * D-pad Right button icon.
 *
 * Icon representing the right direction button of a modern directional pad.
 */
val LudensIcons.Filled.DpadRight: ImageVector
    get() {
        if (_DpadRight != null) {
            return _DpadRight!!
        }
        _DpadRight =
            ImageVector.Builder(
                name = "Filled.DpadRight",
                defaultWidth = 100.dp,
                defaultHeight = 100.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).apply {
                path(fill = SolidColor(Color.Gray.copy(alpha = 0.2f))) {
                    moveToRelative(23.76f, 14.579f)
                    lineToRelative(-0f, -5.16f)
                    lineToRelative(-0.84f, -0.84f)
                    lineToRelative(-7.14f, -0f)
                    lineToRelative(-3.42f, 3.42f)
                    lineToRelative(3.42f, 3.42f)
                    lineToRelative(7.14f, -0f)
                    close()
                }
                path(fill = SolidColor(Color.LightGray.copy(alpha = 0.2f))) {
                    moveToRelative(23.64f, 14.489f)
                    lineToRelative(-0f, -4.979f)
                    lineToRelative(-0.822f, -0.811f)
                    lineToRelative(-6.99f, -0f)
                    lineToRelative(-3.348f, 3.3f)
                    lineToRelative(3.348f, 3.3f)
                    lineToRelative(6.99f, -0f)
                    close()
                }
                path(fill = SolidColor(Color.Gray.copy(alpha = 0.2f))) {
                    moveToRelative(19.98f, 13.14f)
                    lineToRelative(2.28f, -1.2f)
                    lineToRelative(-2.28f, -1.2f)
                    lineToRelative(-0.72f, -0f)
                    lineToRelative(-0f, 2.4f)
                    close()
                }
            }.build()

        return _DpadRight!!
    }

@Suppress("ObjectPropertyName")
private var _DpadRight: ImageVector? = null
