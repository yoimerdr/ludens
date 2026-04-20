package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Weather Moon icon.
 *
 * This icon is a transformation of the filled `Weather Moon` icon from Fluent Icons.
 */
val LudensIcons.Filled.WeatherMoon: ImageVector
    get() {
        if (_WeatherMoon != null) {
            return _WeatherMoon!!
        }
        _WeatherMoon = ImageVector.Builder(
            name = "WeatherMoon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(20.026f, 17.001f)
                curveToRelative(-2.762f, 4.784f, -8.879f, 6.423f, -13.663f, 3.661f)
                arcTo(9.965f, 9.965f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3.13f, 17.68f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.365f, -1.132f)
                curveToRelative(3.767f, -1.348f, 5.785f, -2.91f, 6.956f, -5.146f)
                curveToRelative(1.232f, -2.353f, 1.551f, -4.93f, 0.689f, -8.463f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.769f, -0.927f)
                arcToRelative(9.961f, 9.961f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.457f, 1.327f)
                curveToRelative(4.784f, 2.762f, 6.423f, 8.879f, 3.66f, 13.662f)
                close()
            }
        }.build()

        return _WeatherMoon!!
    }

@Suppress("ObjectPropertyName")
private var _WeatherMoon: ImageVector? = null
