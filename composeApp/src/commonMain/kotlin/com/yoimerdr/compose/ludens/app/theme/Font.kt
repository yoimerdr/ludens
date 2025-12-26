package com.yoimerdr.compose.ludens.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.inter_black
import ludens.composeapp.generated.resources.inter_light
import ludens.composeapp.generated.resources.inter_medium
import ludens.composeapp.generated.resources.inter_regular
import ludens.composeapp.generated.resources.inter_semibold
import ludens.composeapp.generated.resources.outfit_black
import ludens.composeapp.generated.resources.outfit_light
import ludens.composeapp.generated.resources.outfit_medium
import ludens.composeapp.generated.resources.outfit_regular
import ludens.composeapp.generated.resources.outfit_semibold
import org.jetbrains.compose.resources.Font

@Suppress("ObjectPropertyName")
private var _Outfit: FontFamily? = null

@Suppress("ObjectPropertyName")
private var _Inter: FontFamily? = null


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
                    Res.font.inter_black,
                    weight = FontWeight.Black
                ),
                Font(
                    Res.font.inter_semibold,
                    weight = FontWeight.SemiBold
                )
            )
        }

        return _Inter!!
    }