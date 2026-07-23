package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Speaker Mute icon.
 *
 * This icon is a transformation of the outlined `Speaker Mute` icon from Fluent Icons.
 */
val LudensIcons.Outlined.SpeakerMute: ImageVector
    get() {
        if (_SpeakerMute != null) {
            return _SpeakerMute!!
        }
        _SpeakerMute =
            ImageVector.Builder(
                name = "Outlined.SpeakerMute",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).apply {
                path(fill = SolidColor(Color.White)) {
                    moveTo(12.92f, 3.316f)
                    curveToRelative(0.806f, -0.717f, 2.08f, -0.145f, 2.08f, 0.934f)
                    verticalLineToRelative(15.496f)
                    curveToRelative(0f, 1.078f, -1.274f, 1.65f, -2.08f, 0.934f)
                    lineToRelative(-4.492f, -3.994f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        -0.498f,
                        -0.19f
                    )
                    lineTo(4.25f, 16.496f)
                    arcTo(
                        2.25f,
                        2.25f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        2f,
                        14.247f
                    )
                    lineTo(2f, 9.75f)
                    arcToRelative(
                        2.25f,
                        2.25f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        2.25f,
                        -2.25f
                    )
                    horizontalLineToRelative(3.68f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        0.498f,
                        -0.19f
                    )
                    lineToRelative(4.491f, -3.993f)
                    close()
                    moveTo(13.5f, 4.806f)
                    lineTo(9.425f, 8.43f)
                    arcTo(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 7.93f, 9f)
                    lineTo(4.25f, 9f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        -0.75f,
                        0.75f
                    )
                    verticalLineToRelative(4.497f)
                    curveToRelative(0f, 0.415f, 0.336f, 0.75f, 0.75f, 0.75f)
                    horizontalLineToRelative(3.68f)
                    arcToRelative(
                        2.25f,
                        2.25f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        1.495f,
                        0.57f
                    )
                    lineToRelative(4.075f, 3.623f)
                    lineTo(13.5f, 4.807f)
                    close()
                    moveTo(16.22f, 9.22f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        1.06f,
                        0f
                    )
                    lineTo(19f, 10.94f)
                    lineToRelative(1.72f, -1.72f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = true,
                        isPositiveArc = true,
                        1.06f,
                        1.06f
                    )
                    lineTo(20.06f, 12f)
                    lineToRelative(1.72f, 1.72f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = true,
                        isPositiveArc = true,
                        -1.06f,
                        1.06f
                    )
                    lineTo(19f, 13.06f)
                    lineToRelative(-1.72f, 1.72f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = true,
                        isPositiveArc = true,
                        -1.06f,
                        -1.06f
                    )
                    lineTo(17.94f, 12f)
                    lineToRelative(-1.72f, -1.72f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        0f,
                        -1.06f
                    )
                    close()
                }
            }.build()

        return _SpeakerMute!!
    }

@Suppress("ObjectPropertyName")
private var _SpeakerMute: ImageVector? = null
