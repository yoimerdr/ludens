package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Handle left icon.
 * */
val LudensIcons.Outlined.HandleLeft: ImageVector
    get() {
        if (_HandleLeft != null) {
            return _HandleLeft!!
        }
        _HandleLeft = ImageVector.Builder(
            name = "HandleLeft",
            defaultWidth = 8.dp,
            defaultHeight = 40.dp,
            viewportWidth = 8f,
            viewportHeight = 40f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.5028f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveToRelative(1.75f, 1.25f)
                curveToRelative(6f, 9.76f, 6f, 27.74f, 0f, 37.5f)
            }
        }.build()

        return _HandleLeft!!
    }

@Suppress("ObjectPropertyName")
private var _HandleLeft: ImageVector? = null
