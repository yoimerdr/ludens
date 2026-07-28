package com.yoimerdr.compose.ludens.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.FontFamily
import com.yoimerdr.compose.ludens.generated.resources.BodyFont
import com.yoimerdr.compose.ludens.generated.resources.DisplayFont

@Suppress("ObjectPropertyName")
private var _Display: FontFamily? = null

@Suppress("ObjectPropertyName")
private var _Body: FontFamily? = null


val DisplayFontFamily: FontFamily
    @Stable
    @Composable
    get() {
        return (_Display ?: DisplayFont).let {
            _Display = it
            it
        }
    }

val BodyFontFamily: FontFamily
    @Stable
    @Composable
    get() {
        return (_Body ?: BodyFont).let {
            _Body = it
            it
        }
    }
