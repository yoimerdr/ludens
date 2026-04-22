package com.yoimerdr.compose.ludens.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.inter_black
import ludens.composeapp.generated.resources.inter_bold
import ludens.composeapp.generated.resources.inter_italic
import ludens.composeapp.generated.resources.inter_light
import ludens.composeapp.generated.resources.inter_medium
import ludens.composeapp.generated.resources.inter_regular
import ludens.composeapp.generated.resources.inter_semibold
import ludens.composeapp.generated.resources.outfit_black
import ludens.composeapp.generated.resources.outfit_light
import ludens.composeapp.generated.resources.outfit_medium
import ludens.composeapp.generated.resources.outfit_regular
import ludens.composeapp.generated.resources.outfit_semibold
import ludens.composeapp.generated.resources.plusjakartasans_bold
import ludens.composeapp.generated.resources.plusjakartasans_italic
import ludens.composeapp.generated.resources.plusjakartasans_light
import ludens.composeapp.generated.resources.plusjakartasans_medium
import ludens.composeapp.generated.resources.plusjakartasans_regular
import ludens.composeapp.generated.resources.plusjakartasans_semibold
import org.jetbrains.compose.resources.Font

@Suppress("ObjectPropertyName")
private var _Outfit: FontFamily? = null

@Suppress("ObjectPropertyName")
private var _Inter: FontFamily? = null

@Suppress("ObjectPropertyName")
private var _PlusJakartaSans: FontFamily? = null


val Outfit: FontFamily
    @Stable
    @Composable
    get() {
        if (_Outfit == null) {
            _Outfit = FontFamily(
                Font(
                    Res.font.outfit_light,
                    weight = FontWeight.Light
                ),
                Font(
                    Res.font.outfit_regular,
                    weight = FontWeight.Normal
                ),
                Font(
                    Res.font.outfit_medium,
                    weight = FontWeight.Medium
                ),
                Font(
                    Res.font.outfit_black,
                    weight = FontWeight.Black
                ),
                Font(
                    Res.font.outfit_semibold,
                    weight = FontWeight.SemiBold
                )
            )
        }
        return _Outfit!!
    }

val Inter: FontFamily
    @Stable
    @Composable
    get() {
        if (_Inter == null) {
            _Inter = FontFamily(
                Font(
                    Res.font.inter_light,
                    weight = FontWeight.Light
                ),
                Font(
                    Res.font.inter_regular,
                    weight = FontWeight.Normal
                ),
                Font(
                    Res.font.inter_medium,
                    weight = FontWeight.Medium
                ),
                Font(
                    Res.font.inter_bold,
                    weight = FontWeight.Bold
                ),
                Font(
                    Res.font.inter_black,
                    weight = FontWeight.Black
                ),
                Font(
                    Res.font.inter_semibold,
                    weight = FontWeight.SemiBold
                ),
                Font(
                    Res.font.inter_italic,
                    weight = FontWeight.Normal
                )
            )
        }

        return _Inter!!
    }

val PlusJakartaSans: FontFamily
    @Stable
    @Composable
    get() {
        if (_PlusJakartaSans == null) {
            _PlusJakartaSans = FontFamily(
                Font(
                    Res.font.plusjakartasans_light,
                    weight = FontWeight.Light
                ),
                Font(
                    Res.font.plusjakartasans_regular,
                    weight = FontWeight.Normal
                ),
                Font(
                    Res.font.plusjakartasans_medium,
                    weight = FontWeight.Medium
                ),
                Font(
                    Res.font.plusjakartasans_bold,
                    weight = FontWeight.Bold
                ),
                Font(
                    Res.font.plusjakartasans_semibold,
                    weight = FontWeight.SemiBold
                ),
                Font(
                    Res.font.plusjakartasans_italic,
                    weight = FontWeight.Normal
                )
            )
        }
        return _PlusJakartaSans!!
    }

val DisplayFontFamily: FontFamily
    @Stable
    @Composable
    get() = PlusJakartaSans

val BodyFontFamily: FontFamily
    @Stable
    @Composable
    get() = Inter
