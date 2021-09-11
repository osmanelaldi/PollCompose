package com.example.pollcompose.util

import android.annotation.SuppressLint
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("dd-M-yyyy hh:mm:ss")
    @SuppressLint("SimpleDateFormat")
    private val prettySdf = SimpleDateFormat("dd MMMM")

    fun longToDate(long: Long): Date {
        return Date(long)
    }

    fun dateToLong(date: Date): Long {
        return date.time / 1000 // return seconds
    }

    // Ex: November 4, 2021
    fun dateToString(date: Date): String{
        return sdf.format(date)
    }

    fun stringToDate(string: String): Date {
        return sdf.parse(string)!!
    }

    fun createTimestamp(): Date {
        return Date()
    }

    fun getPrettyDate(strDate : String) : String{
        val date = stringToDate(strDate)
        return prettySdf.format(date)
    }
}