package com.example.assessment.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


object DateAndTimeUtils {


    fun formatDate(date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
        val formatter = SimpleDateFormat("yyyy-MM-dd",Locale("en"))
        val output = formatter.format(parser.parse(date))
        return output
    }

    @SuppressLint("DefaultLocale")
    fun formatToDoubleDigitOfMonthAndDay(year: Int, month: Int, day: Int): String {
        val monthFormat = java.lang.String.format("%02d", month)
        val dayFormat = java.lang.String.format("%02d", day)

        return "$year-$monthFormat-$dayFormat"
    }
    fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        val mdFormat = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
        return mdFormat.format(calendar.time)
    }

}