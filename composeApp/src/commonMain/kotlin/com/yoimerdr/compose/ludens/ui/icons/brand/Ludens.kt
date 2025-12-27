package com.yoimerdr.compose.ludens.ui.icons.brand

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Ludens brand logo icon.
 *
 * Custom brand icon representing the Ludens application identity.
 * Features a gradient color scheme characteristic of the Ludens brand.
 */
val LudensIcons.Brand.Ludens: ImageVector
    get() {
        if (_Ludens != null) {
            return _Ludens!!
        }
        _Ludens = ImageVector.Builder(
            name = "Brand.Ludens",
            defaultWidth = 48.dp,
            defaultHeight = 48.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = Brush.linearGradient(
                    colorStops = arrayOf(
                        0f to Color(0xFF81D0FB),
                        1f to Color(0xFF7C86F9)
                    ),
                    start = Offset(3.237f, 17.163f),
                    end = Offset(7.699f, 17.163f)
                ),
                stroke = SolidColor(Color.White),
                strokeLineWidth = 0.128f
            ) {
                moveToRelative(7.637f, 21.054f)
                lineToRelative(0f, -5.197f)
                lineToRelative(-4.337f, -2.586f)
                lineToRelative(0f, 5.185f)
                close()
            }
            path(
                fill = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.433f to Color(0xFF81D0FB),
                        1f to Color(0xFFC461FA)
                    ),
                    start = Offset(3.232f, 11.985f),
                    end = Offset(12.056f, 11.985f)
                ),
                stroke = SolidColor(Color.White),
                strokeLineWidth = 0.128f
            ) {
                moveToRelative(7.632f, 15.876f)
                lineToRelative(0f, -5.197f)
                lineToRelative(-4.337f, -2.586f)
                lineToRelative(0f, 5.185f)
                close()
                moveTo(11.995f, 8.103f)
                lineTo(7.636f, 10.701f)
                lineTo(7.636f, 15.873f)
                lineTo(11.985f, 13.28f)
                close()
            }
            path(
                fill = Brush.linearGradient(
                    colorStops = arrayOf(
                        0f to Color(0xFF81D0FB),
                        1f to Color(0xFFC461FA)
                    ),
                    start = Offset(3.187f, 5.522f),
                    end = Offset(12.133f, 5.522f)
                ),
                stroke = SolidColor(Color.White),
                strokeLineWidth = 0.128f
            ) {
                moveToRelative(7.65f, 10.683f)
                lineToRelative(0f, -5.197f)
                lineToRelative(-4.337f, -2.586f)
                lineToRelative(0f, 5.185f)
                close()
                moveTo(12.013f, 2.91f)
                lineTo(7.654f, 5.508f)
                lineTo(7.654f, 10.68f)
                lineTo(12.003f, 8.087f)
                close()
                moveTo(3.313f, 2.903f)
                lineTo(7.672f, 5.501f)
                lineTo(12.009f, 2.916f)
                lineTo(7.66f, 0.323f)
                close()
            }
            path(
                fill = Brush.linearGradient(
                    colorStops = arrayOf(
                        0f to Color(0xFF81D0FB),
                        0.719f to Color(0xFFC461FA)
                    ),
                    start = Offset(11.865f, 15.891f),
                    end = Offset(20.812f, 15.891f)
                ),
                stroke = SolidColor(Color.White),
                strokeLineWidth = 0.128f
            ) {
                moveToRelative(20.691f, 13.28f)
                lineToRelative(-4.359f, 2.598f)
                lineToRelative(-0f, 5.171f)
                lineToRelative(4.349f, -2.593f)
                close()
                moveTo(11.992f, 13.273f)
                lineTo(16.35f, 15.871f)
                lineTo(20.687f, 13.286f)
                lineTo(16.339f, 10.693f)
                close()
            }
            path(
                fill = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.266f to Color(0xFF81D0FB),
                        1f to Color(0xFFC461FA)
                    ),
                    start = Offset(7.512f, 18.475f),
                    end = Offset(16.458f, 18.475f)
                ),
                stroke = SolidColor(Color.White),
                strokeLineWidth = 0.128f
            ) {
                moveToRelative(11.975f, 23.636f)
                lineToRelative(0f, -5.197f)
                lineToRelative(-4.337f, -2.586f)
                lineToRelative(0f, 5.185f)
                close()
                moveTo(16.338f, 15.863f)
                lineTo(11.979f, 18.461f)
                lineTo(11.979f, 23.633f)
                lineTo(16.328f, 21.04f)
                close()
                moveTo(7.638f, 15.856f)
                lineTo(11.997f, 18.454f)
                lineTo(16.334f, 15.869f)
                lineTo(11.985f, 13.276f)
                close()
            }
        }.build()

        return _Ludens!!
    }

@Suppress("ObjectPropertyName")
private var _Ludens: ImageVector? = null
