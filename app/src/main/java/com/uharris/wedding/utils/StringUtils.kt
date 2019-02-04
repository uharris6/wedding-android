package com.uharris.wedding.utils

import java.util.*
import java.util.concurrent.TimeUnit

object StringUtils {

    fun hoursTime(millisUntilFinished: Long): Long {
        var time = millisUntilFinished

        val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
        time -= TimeUnit.DAYS.toMillis(days)

        return time
    }

    fun daysTime(millisUntilFinished: Long): String {
        var time = millisUntilFinished

        val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
        time -= TimeUnit.DAYS.toMillis(days)

        // Format the string
        return String.format(
            Locale.getDefault(),
            "%02d",
            days)
    }

    fun timeString(millisUntilFinished:Long) : String {

        var time = millisUntilFinished

        val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
        time -= TimeUnit.DAYS.toMillis(days)

        val hours = TimeUnit.MILLISECONDS.toHours(time)
        time -= TimeUnit.HOURS.toMillis(hours)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(time)
        time -= TimeUnit.MINUTES.toMillis(minutes)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(time)

        // Format the string
        return String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d:%02d",
            days,hours,minutes, seconds
        )
    }
}