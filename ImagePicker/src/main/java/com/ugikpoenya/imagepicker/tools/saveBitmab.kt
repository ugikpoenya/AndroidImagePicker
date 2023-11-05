package com.ugikpoenya.imagepicker.tools

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream


fun saveBitmap(bitmap: Bitmap?): String {
    return saveBitmap(bitmap, getRandomFilename())
}

fun saveBitmap(bitmap: Bitmap?, filename: String): String {
    val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val fileImage = File(imagesDir, filename)
    return saveBitmap(bitmap, fileImage)
}

fun saveBitmap(bitmap: Bitmap?, filename: String, imagesDir: File): String {
    if (!imagesDir.exists()) {
        imagesDir.mkdir()
    }
    val fileImage = File(imagesDir, filename)
    return saveBitmap(bitmap, fileImage)
}

fun saveBitmap(bitmap: Bitmap?, fileImage: File): String {
    if (fileImage.exists()) fileImage.delete()
    Log.d("LOG", "Save image " + fileImage.path)
    val fOut = FileOutputStream(fileImage)
    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, fOut)
    fOut.flush()
    fOut.close()
    return fileImage.path
}