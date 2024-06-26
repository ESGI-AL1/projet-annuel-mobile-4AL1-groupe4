package com.example.acad.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val SERVER_IP_BASE = "192.168.43.101:8000"
//const val SERVER_IP_BASE = "172.17.0.1:8000"
//const val SERVER_IP_BASE = "172.16.10.150:8000"

fun Date.format(format: String, date: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, date)
    return formatter.format(this)
}

fun String.formatDate(format: String, locale: Locale = Locale.getDefault()): String =
    if (this.isBlank()) {
        ""
    } else {
        val formatter = SimpleDateFormat("dd/MM/yyyy", locale)
        val localeDate = formatter.parse(this)
        "${localeDate?.format(format)}"
    }

fun String.parseDate(format: String, locale: Locale = Locale.getDefault()): Date? {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.parse(this)
}

//fun Long.toDate(format: String, locale: Locale = Locale.getDefault()): String = try {
//    val date = Date.from(Instant.ofEpochMilli(this))
//    val formatter = SimpleDateFormat(format, locale)
//    formatter.format(date)
//} catch (e: Exception) {
//    e.printStackTrace()
//    ""
//}