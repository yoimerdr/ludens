package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Weather Moon icon.
 *
 * This icon is a transformation of the outlined `Weather Moon` icon from Fluent Icons.
 */
val LudensIcons.Outlined.WeatherMoon: ImageVector
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
                arcToRelative(9.964f, 9.964f, 0f, isMoreThanHalf = false, isPositiveArc = true, -3.234f, -2.983f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.365f, -1.131f)
                curveToRelative(3.767f, -1.348f, 5.785f, -2.911f, 6.956f, -5.146f)
                curveToRelative(1.232f, -2.353f, 1.551f, -4.93f, 0.689f, -8.464f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.769f, -0.926f)
                arcToRelative(9.961f, 9.961f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.457f, 1.327f)
                curveTo(21.149f, 6.1f, 22.788f, 12.217f, 20.025f, 17f)
                close()
                moveTo(11.778f, 12.098f)
                curveToRelative(-1.25f, 2.388f, -3.31f, 4.099f, -6.817f, 5.499f)
                arcToRelative(8.492f, 8.492f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.152f, 1.766f)
                arcToRelative(8.501f, 8.501f, 0f, isMoreThanHalf = true, isPositiveArc = false, 8.502f, -14.725f)
                arcToRelative(8.485f, 8.485f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2.792f, -1.016f)
                curveToRelative(0.647f, 3.384f, 0.23f, 6.044f, -1.045f, 8.476f)
                close()
            }
        }.build()

        return _WeatherMoon!!
    }

@Suppress("ObjectPropertyName")
private var _WeatherMoon: ImageVector? = null
