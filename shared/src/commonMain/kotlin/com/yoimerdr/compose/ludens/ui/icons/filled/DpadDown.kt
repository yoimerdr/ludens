package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * D-pad Down button icon.
 *
 * Icon representing the down direction button of a modern directional pad.
 * */
val LudensIcons.Filled.DpadDown: ImageVector
    get() {
        if (_DpadDown != null) {
            return _DpadDown!!
        }
        _DpadDown =
            ImageVector.Builder(
                name = "Filled.DpadDown",
                defaultWidth = 100.dp,
                defaultHeight = 100.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).apply {
                path(fill = SolidColor(Color.Gray.copy(alpha = 0.2f))) {
                    moveToRelative(9.421f, 23.76f)
                    lineToRelative(5.16f, -0f)
                    lineToRelative(0.84f, -0.84f)
                    lineToRelative(0f, -7.14f)
                    lineToRelative(-3.42f, -3.42f)
                    lineToRelative(-3.42f, 3.42f)
                    lineToRelative(0f, 7.14f)
                    close()
                }
                path(fill = SolidColor(Color.LightGray.copy(alpha = 0.2f))) {
                    moveToRelative(9.511f, 23.64f)
                    lineToRelative(4.979f, -0f)
                    lineToRelative(0.811f, -0.822f)
                    lineToRelative(0f, -6.99f)
                    lineToRelative(-3.3f, -3.348f)
                    lineToRelative(-3.3f, 3.348f)
                    lineToRelative(0f, 6.99f)
                    close()
                }
                path(fill = SolidColor(Color.Gray.copy(alpha = 0.2f))) {
                    moveToRelative(10.86f, 19.98f)
                    lineToRelative(1.2f, 2.28f)
                    lineToRelative(1.2f, -2.28f)
                    lineToRelative(0f, -0.72f)
                    lineToRelative(-2.4f, -0f)
                    close()
                }
            }.build()

        return _DpadDown!!
    }

@Suppress("ObjectPropertyName")
private var _DpadDown: ImageVector? = null
