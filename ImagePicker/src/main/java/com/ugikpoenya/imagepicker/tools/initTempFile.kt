package com.ugikpoenya.imagepicker.tools

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

fun initTempFile(context: Context): File {
    val random = (100..999).random()
    val storageDir = context.getExternalFilesDir("temp")
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    return File.createTempFile(
        "${timeStamp}_${random}", /* prefix */
        ".png", /* suffix */
        storageDir /* directory */
    )
}
