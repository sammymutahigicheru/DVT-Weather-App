package com.dvt.core.helpers

import java.text.SimpleDateFormat
import java.util.*

fun convertToDay(currentTime: Long): String {
    //Monday
    val epoch = currentTime * 1000

    val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())

    return dateFormat.format(Date(epoch))
}