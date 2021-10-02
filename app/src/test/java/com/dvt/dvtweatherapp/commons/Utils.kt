package com.dvt.dvtweatherapp.commons

import com.google.common.io.Resources
import java.io.File

internal fun getJson(path: String): String {
    val uri = Resources.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}