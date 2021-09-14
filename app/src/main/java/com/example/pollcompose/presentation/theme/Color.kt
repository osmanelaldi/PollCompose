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
    open val key = ""
    open val light =  White
    open val medium =  GrayLight
    open val dark = Brown

    companion object{
        fun getSchemes() = listOf(BlueScheme,OrangeScheme,YellowScheme,PurpleScheme,GreenScheme)
        fun getScheme(key : String) = getSchemes().find { it.key == key}!!
    }

    object BlueScheme : ColorScheme() {
        override val key = "Blue"
        override val light =  Color(0XFFf1f5ff)
        override val medium = Color(0XFFa5bfff)
        override val dark = Color(0XFF729bff)
    }
    object OrangeScheme : ColorScheme(){
        override val key = "Orange"
        override val light =  Color(0XFFffd7b5)
        override val medium = Color(0XFFff9248)
        override val dark = Color(0XFFff6700)
    }

    object YellowScheme : ColorScheme(){
        override val key = "Yellow"
        override val light =  Color(0XFFfaf8c5)
        override val medium = Color(0XFFfffb94)
        override val dark = Color(0XFFfffa6e)
    }

    object PurpleScheme : ColorScheme(){
        override val key = "Purple"
        override val light =  Color(0XFFe3bbff)
        override val medium = Color(0XFFd79eff)
        override val dark = Color(0XFFc876ff)
    }

    object GreenScheme : ColorScheme(){
        override val key = "Green"
        override val light =  Color(0XFFdbffef)
        override val medium = Color(0XFF99ffc9)
        override val dark = Color(0XFF83e7a8)
    }
}

