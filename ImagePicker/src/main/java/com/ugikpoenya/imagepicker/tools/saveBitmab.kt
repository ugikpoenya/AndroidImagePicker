package com.ugikpoenya.imagepicker.tools

import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date

fun saveBitmap(bitmap: Bitmap?): String {
    val random = (100..999).random()
    val timeStamp: String = SimpleDateFormat("yyyy-MM-dd_HHmmss").format(Date())
    val filename = "${timeStamp}_${random}.png"
    return saveBitmap(bitmap, filename)
}

fun saveBitmap(bitmap: Bitmap?, filename: String): String {
    val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val fileImage = File(imagesDir, filename)
    return saveBitmap(bitmap, fileImage)
}

fun saveBitmap(bitmap: Bitmap?, fileImage: File): String {
    val fOut = FileOutputStream(fileImage)
    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, fOut)
    fOut.flush()
    fOut.close()
    return fileImage.path
}