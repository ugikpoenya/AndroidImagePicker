package com.ugikpoenya.imagepicker.tools

import java.text.SimpleDateFormat
import java.util.Date

fun getRandomFilename(): String {
    val random = (100..999).random()
    val timeStamp: String = SimpleDateFormat("yyyy-MM-dd_HHmmss").format(Date())
    val filename = "${timeStamp}_${random}.png"
    return filename
}