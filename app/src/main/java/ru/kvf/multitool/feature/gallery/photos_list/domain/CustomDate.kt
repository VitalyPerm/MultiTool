package ru.kvf.multitool.feature.gallery.photos_list.domain

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CustomDate(
    private val date: Calendar
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CustomDate

        val otherYear = other.date.get(Calendar.YEAR)
        val otherDayOfYear = other.date.get(Calendar.DAY_OF_YEAR)

        val year = date.get(Calendar.YEAR)
        val dayOfYear = date.get(Calendar.DAY_OF_YEAR)

        if (otherYear != year && otherDayOfYear != dayOfYear) return false

        return true
    }

    override fun hashCode(): Int {
        val year = date.get(Calendar.YEAR)
        val dayOfYear = date.get(Calendar.DAY_OF_YEAR)
        return year.hashCode() + dayOfYear.hashCode()
    }

    override fun toString(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY)

        val dayOfWeek = date.get(Calendar.DAY_OF_WEEK)
        val dayOfMonth = date.get(Calendar.DAY_OF_MONTH)
        val month = date.get(Calendar.MONTH)
        return sdf.format(date.time)
    }
}