package com.example.chronos.views.utils

import com.example.chronos.data.models.Date
import java.util.*

object DateHelper
{
    fun getCurrentDate() : Date
    {
        val calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val month: Int = calendar.get(Calendar.MONTH) + 1
        val year: Int = calendar.get(Calendar.YEAR)
        return Date(day, month, year)
    }

    fun format(date: Date) : String
    {
        val day: String = if (date.day > 9) date.day.toString() else "0${date.day}"
        val month: String = if (date.month > 9) date.day.toString() else "0${date.month}"
        return "$day/$month/${date.year}"
    }
}