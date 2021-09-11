package com.example.pollcompose.presentation.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val GreenLight = Color(0XFF7FC8A9)
val Brown = Color(0XFF444941)
val GrayLight = Color(0XFFDCDCDC)
val ErrorRed = Color(0XFFFF3F2A)
val Blue = Color(0XFF2084f6)
val White = Color(0xFFFFFFFF)


sealed class ColorScheme {
    open val light =  White
    open val medium =  GrayLight
    open val dark = Brown

    object BlueScheme : ColorScheme() {
        override val light =  Color(0XFFf1f5ff)
        override val medium = Color(0XFFa5bfff)
        override val dark = Color(0XFF729bff)
    }
}