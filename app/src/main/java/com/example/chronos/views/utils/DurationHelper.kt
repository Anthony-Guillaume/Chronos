package com.example.chronos.views.utils

object DurationHelper
{
    fun format(durationMs: Long) : String
    {
        val durationSeconds: Long = durationMs / 1000
        val minute: Long = durationSeconds / 60
        val seconds: Long = durationSeconds % 60
        val minuteFormatted: String = if (minute > 9)  "$minute" else "0$minute"
        val secondsFormatted: String = if (seconds > 9)  "$seconds" else "0$seconds"
        return "$minuteFormatted : $secondsFormatted"
    }
}