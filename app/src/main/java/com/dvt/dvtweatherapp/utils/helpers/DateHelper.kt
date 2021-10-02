package com.dvt.dvtweatherapp.utils.helpers

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

fun convertToDay(currentTime: Long): String {
    //Monday
    val epoch = currentTime * 1000

    val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())

    return dateFormat.format(Date(epoch))
}