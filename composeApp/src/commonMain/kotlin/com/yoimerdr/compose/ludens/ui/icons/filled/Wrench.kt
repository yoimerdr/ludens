package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Wrench icon.
 *
 * This icon is a transformation of the filled `Wrench` icon from Fluent Icons.
 */
val LudensIcons.Filled.Wrench: ImageVector
    get() {
        if (_Wrench != null) {
            return _Wrench!!
        }
        _Wrench = ImageVector.Builder(
            name = "Wrench",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(16.75f, 2.001f)
                arcToRelative(5.25f, 5.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, -5.005f, 6.84f)
                lineToRelative(-9.068f, 9.38f)
                arcToRelative(2.344f, 2.344f, 0f, isMoreThanHalf = true, isPositiveArc = false, 3.37f, 3.257f)
                lineToRelative(8.963f, -9.272f)
                arcTo(5.25f, 5.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 21.797f, 5.8f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.25f, -0.323f)
                lineTo(17.36f, 8.66f)
                lineToRelative(-2.06f, -2.06f)
                lineToRelative(3.16f, -3.162f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.333f, -1.254f)
                arcToRelative(5.255f, 5.255f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.378f, -0.183f)
                close()
            }
        }.build()

        return _Wrench!!
    }

@Suppress("ObjectPropertyName")
private var _Wrench: ImageVector? = null
