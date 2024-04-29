package com.byteapps.serrvicewala.UIComponents

import android.os.Build
import java.text.SimpleDateFormat
import java.time.Month
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

data class Time(
    val time: String,
    val format: String
)
val timeList = mutableListOf<Time>(
    Time("8 - 9", "AM"),
    Time("9 - 10", "AM"),
    Time("10 - 11", "AM"),
    Time("11 - 12", "AM"),
    Time("12 - 1", "PM"),
    Time("1 - 2", "PM"),
    Time("2 - 3", "PM"),
    Time("3 - 4", "PM"),
    Time( "4 - 5", "PM"),

 )



fun generateCurrentMonthDates(): List<DateDataModel> {
    val toDayDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    val currentMonthDates = mutableListOf<DateDataModel>()
    val currentDate = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, toDayDate)
    }
    val lastDayOfMonth = currentDate.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())

    repeat(lastDayOfMonth) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentMonthDates.add(
                DateDataModel(
                    day_of_month = currentDate.get(java.util.Calendar.DAY_OF_MONTH),
                    month = (currentDate.get(Calendar.MONTH) + 1).let { monthIndex ->
                        Month.of(monthIndex).getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    },
                    day_of_week = dayFormat.format(currentDate.time),
                    year = currentDate.get(java.util.Calendar.YEAR)
                )

            )
        }
        currentDate.add(java.util.Calendar.DAY_OF_MONTH, 1)
    }

    return currentMonthDates
}

data class DateDataModel(
    val day_of_month : Int = 0,
    val month : String = "",
    val day_of_week : String = "",
    val year : Int = 0,
)