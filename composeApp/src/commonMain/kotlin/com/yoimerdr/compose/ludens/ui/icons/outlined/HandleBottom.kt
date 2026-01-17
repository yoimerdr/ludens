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
 * Handle bottom icon.
 * */
val LudensIcons.Outlined.HandleBottom: ImageVector
    get() {
        if (_HandleBottom != null) {
            return _HandleBottom!!
        }
        _HandleBottom = ImageVector.Builder(
            name = "HandleBottom",
            defaultWidth = 40.dp,
            defaultHeight = 8.dp,
            viewportWidth = 40f,
            viewportHeight = 8f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.503f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveToRelative(1.25f, 6.248f)
                curveToRelative(9.76f, -5.998f, 27.74f, -5.998f, 37.5f, 0f)
            }
        }.build()

        return _HandleBottom!!
    }

@Suppress("ObjectPropertyName")
private var _HandleBottom: ImageVector? = null
