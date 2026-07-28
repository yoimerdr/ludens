package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Spinner icon.
 *
 * This icon is a transformation of a public `Spinner` svg icon.
 */
val LudensIcons.Filled.Spinner: ImageVector
    get() {
        if (_Spinner != null) {
            return _Spinner!!
        }
        _Spinner =
            ImageVector.Builder(
                name = "Filled.Spinner",
                defaultWidth = 32.dp,
                defaultHeight = 32.dp,
                viewportWidth = 640f,
                viewportHeight = 640f
            ).apply {
                path(fill = SolidColor(Color.Black)) {
                    moveTo(272f, 112f)
                    curveTo(272f, 85.5f, 293.5f, 64f, 320f, 64f)
                    curveTo(346.5f, 64f, 368f, 85.5f, 368f, 112f)
                    curveTo(368f, 138.5f, 346.5f, 160f, 320f, 160f)
                    curveTo(293.5f, 160f, 272f, 138.5f, 272f, 112f)
                    close()
                    moveTo(272f, 528f)
                    curveTo(272f, 501.5f, 293.5f, 480f, 320f, 480f)
                    curveTo(346.5f, 480f, 368f, 501.5f, 368f, 528f)
                    curveTo(368f, 554.5f, 346.5f, 576f, 320f, 576f)
                    curveTo(293.5f, 576f, 272f, 554.5f, 272f, 528f)
                    close()
                    moveTo(112f, 272f)
                    curveTo(138.5f, 272f, 160f, 293.5f, 160f, 320f)
                    curveTo(160f, 346.5f, 138.5f, 368f, 112f, 368f)
                    curveTo(85.5f, 368f, 64f, 346.5f, 64f, 320f)
                    curveTo(64f, 293.5f, 85.5f, 272f, 112f, 272f)
                    close()
                    moveTo(480f, 320f)
                    curveTo(480f, 293.5f, 501.5f, 272f, 528f, 272f)
                    curveTo(554.5f, 272f, 576f, 293.5f, 576f, 320f)
                    curveTo(576f, 346.5f, 554.5f, 368f, 528f, 368f)
                    curveTo(501.5f, 368f, 480f, 346.5f, 480f, 320f)
                    close()
                    moveTo(139f, 433.1f)
                    curveTo(157.8f, 414.3f, 188.1f, 414.3f, 206.9f, 433.1f)
                    curveTo(225.7f, 451.9f, 225.7f, 482.2f, 206.9f, 501f)
                    curveTo(188.1f, 519.8f, 157.8f, 519.8f, 139f, 501f)
                    curveTo(120.2f, 482.2f, 120.2f, 451.9f, 139f, 433.1f)
                    close()
                    moveTo(139f, 139f)
                    curveTo(157.8f, 120.2f, 188.1f, 120.2f, 206.9f, 139f)
                    curveTo(225.7f, 157.8f, 225.7f, 188.1f, 206.9f, 206.9f)
                    curveTo(188.1f, 225.7f, 157.8f, 225.7f, 139f, 206.9f)
                    curveTo(120.2f, 188.1f, 120.2f, 157.8f, 139f, 139f)
                    close()
                    moveTo(501f, 433.1f)
                    curveTo(519.8f, 451.9f, 519.8f, 482.2f, 501f, 501f)
                    curveTo(482.2f, 519.8f, 451.9f, 519.8f, 433.1f, 501f)
                    curveTo(414.3f, 482.2f, 414.3f, 451.9f, 433.1f, 433.1f)
                    curveTo(451.9f, 414.3f, 482.2f, 414.3f, 501f, 433.1f)
                    close()
                }
            }.build()

        return _Spinner!!
    }

@Suppress("ObjectPropertyName")
private var _Spinner: ImageVector? = null
