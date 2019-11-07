package com.legion1900.moxynews.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {

    companion object {
        fun dateToFormatStr(date: Date, format: String): String {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            return dateFormat.format(date)
        }
    }

    private val calendar1 = Calendar.getInstance()
    private val calendar2 = Calendar.getInstance()

    /*
    * Subtracts second date from first.
    * */
    fun subtract(first: Date, second: Date): Long {
        calendar1.time = first
        calendar2.time = second
        return calendar1.timeInMillis - calendar2.timeInMillis
    }
}