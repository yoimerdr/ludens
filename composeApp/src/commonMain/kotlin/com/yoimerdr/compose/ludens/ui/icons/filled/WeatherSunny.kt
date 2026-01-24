package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Weather Sunny icon.
 *
 * This icon is a transformation of the filled `Weather Sunny` icon from Fluent Icons.
 */
val LudensIcons.Filled.WeatherSunny: ImageVector
    get() {
        if (_WeatherSunny != null) {
            return _WeatherSunny!!
        }
        _WeatherSunny = ImageVector.Builder(
            name = "WeatherSunny",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(11.996f, 18.532f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.993f, 0.883f)
                lineToRelative(0.007f, 0.117f)
                verticalLineToRelative(1.456f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.993f, 0.116f)
                lineToRelative(-0.007f, -0.116f)
                verticalLineToRelative(-1.456f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, -1f)
                close()
                moveTo(18.033f, 16.6f)
                lineTo(19.063f, 17.63f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.415f, 1.413f)
                lineToRelative(-1.03f, -1.029f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.415f, -1.414f)
                close()
                moveTo(7.373f, 16.6f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 1.414f)
                lineToRelative(-1.029f, 1.03f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.414f, -1.415f)
                lineToRelative(1.03f, -1.03f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.413f, 0f)
                close()
                moveTo(12.01f, 6.472f)
                arcToRelative(5.525f, 5.525f, 0f, isMoreThanHalf = true, isPositiveArc = true, 0f, 11.05f)
                arcToRelative(5.525f, 5.525f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -11.05f)
                close()
                moveTo(20.978f, 11.018f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.117f, 1.993f)
                lineToRelative(-0.117f, 0.007f)
                horizontalLineToRelative(-1.456f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.116f, -1.993f)
                lineToRelative(0.116f, -0.007f)
                horizontalLineToRelative(1.456f)
                close()
                moveTo(4.479f, 10.99f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.116f, 1.993f)
                lineToRelative(-0.116f, 0.007f)
                lineTo(3.023f, 12.99f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.117f, -1.993f)
                lineToRelative(0.117f, -0.007f)
                horizontalLineToRelative(1.456f)
                close()
                moveTo(6.249f, 4.874f)
                lineTo(6.344f, 4.957f)
                lineTo(7.374f, 5.987f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.32f, 1.497f)
                lineTo(5.958f, 7.4f)
                lineTo(4.93f, 6.371f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.32f, -1.497f)
                close()
                moveTo(19.062f, 4.957f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.083f, 1.32f)
                lineToRelative(-0.083f, 0.094f)
                lineToRelative(-1.03f, 1.03f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.497f, -1.32f)
                lineToRelative(0.084f, -0.095f)
                lineToRelative(1.029f, -1.03f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.414f, 0f)
                close()
                moveTo(12f, 2.013f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.993f, 0.883f)
                lineToRelative(0.007f, 0.117f)
                verticalLineToRelative(1.455f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.993f, 0.117f)
                lineTo(11f, 4.468f)
                lineTo(11f, 3.013f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, -1f)
                close()
            }
        }.build()

        return _WeatherSunny!!
    }

@Suppress("ObjectPropertyName")
private var _WeatherSunny: ImageVector? = null
