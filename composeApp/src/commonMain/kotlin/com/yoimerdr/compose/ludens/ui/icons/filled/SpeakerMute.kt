package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Speaker Mute icon.
 *
 * This icon is a transformation of the filled `Speaker Mute` icon from Fluent Icons.
 */
val LudensIcons.Filled.SpeakerMute: ImageVector
    get() {
        if (_SpeakerMute != null) {
            return _SpeakerMute!!
        }
        _SpeakerMute = ImageVector.Builder(
            name = "Filled.SpeakerMute",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(15f, 4.25f)
                curveToRelative(0f, -1.079f, -1.274f, -1.65f, -2.08f, -0.934f)
                lineTo(8.427f, 7.309f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.498f, 0.19f)
                horizontalLineTo(4.25f)
                arcTo(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2f, 9.749f)
                verticalLineToRelative(4.497f)
                arcToRelative(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.25f, 2.25f)
                horizontalLineToRelative(3.68f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.498f, 0.19f)
                lineToRelative(4.491f, 3.994f)
                curveToRelative(0.806f, 0.716f, 2.081f, 0.144f, 2.081f, -0.934f)
                verticalLineTo(4.25f)
                close()
                moveTo(16.22f, 9.22f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.06f, 0f)
                lineTo(19f, 10.94f)
                lineToRelative(1.72f, -1.72f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = true, isPositiveArc = true, 1.06f, 1.06f)
                lineTo(20.06f, 12f)
                lineToRelative(1.72f, 1.72f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = true, isPositiveArc = true, -1.06f, 1.06f)
                lineTo(19f, 13.06f)
                lineToRelative(-1.72f, 1.72f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = true, isPositiveArc = true, -1.06f, -1.06f)
                lineTo(17.94f, 12f)
                lineToRelative(-1.72f, -1.72f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -1.06f)
                close()
            }
        }.build()

        return _SpeakerMute!!
    }

@Suppress("ObjectPropertyName")
private var _SpeakerMute: ImageVector? = null
