package com.ugikpoenya.imagepicker.tools

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF


private fun getScale(source: Bitmap, watermark: Bitmap, scale: Float): Float {
    var scaleM = (source.width.toFloat() * scale / watermark.width.toFloat())
    if (source.width > source.height) scaleM = (source.height.toFloat() * scale / watermark.height.toFloat())
    return scaleM
}

fun addWatermarkBitmap(source: Bitmap, watermark: Bitmap, scale: Float, alpha: Int, margin: Float): Bitmap {
    return addWatermarkBitmap(source, watermark, scale, alpha, margin, margin)
}

fun addWatermarkBitmapCenter(source: Bitmap, watermark: Bitmap, scale: Float, alpha: Int): Bitmap {
    val scaleM = getScale(source, watermark, scale)
    val width = (source.width.toFloat() / 2) - ((scaleM * watermark.width) / 2)
    val height = (source.height.toFloat() / 2) - ((scaleM * watermark.height) / 2)
    return addWatermarkBitmap(source, watermark, scale, alpha, width, height)
}

fun addWatermarkBitmapBottom(source: Bitmap, watermark: Bitmap, scale: Float, alpha: Int, margin: Float): Bitmap {
    val scaleM = getScale(source, watermark, scale)
    val height = source.height.toFloat() - (scaleM * watermark.height) - margin
    return addWatermarkBitmap(source, watermark, scale, alpha, margin, height)
}

fun addWatermarkBitmapEnd(source: Bitmap, watermark: Bitmap, scale: Float, alpha: Int, margin: Float): Bitmap {
    val scaleM = getScale(source, watermark, scale)
    val width = source.width.toFloat() - (scaleM * watermark.width) - margin
    return addWatermarkBitmap(source, watermark, scale, alpha, width, margin)
}

fun addWatermarkBitmapBottomEnd(source: Bitmap, watermark: Bitmap, scale: Float, alpha: Int, margin: Float): Bitmap {
    val scaleM = getScale(source, watermark, scale)
    val width = source.width.toFloat() - (scaleM * watermark.width) - margin
    val height = source.height.toFloat() - (scaleM * watermark.height) - margin
    return addWatermarkBitmap(source, watermark, scale, alpha, width, height)
}


fun addWatermarkBitmap(source: Bitmap, watermark: Bitmap, scale: Float, alpha: Int, dx: Float, dy: Float): Bitmap {
    val width: Int
    val height: Int
    val canvas: Canvas
    val paint: Paint
    val bitmap: Bitmap
    val matrix: Matrix
    val scaleWm: Float
    val rectF: RectF
    width = source.width
    height = source.height

    // Create a new bitmap file and draw it on the canvas
    bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG)
    canvas = Canvas(bitmap)
    canvas.drawBitmap(source, 0f, 0f, paint)


    // scale / adjust height of your logo/watermark
    // i am scaling it down to 30%
    scaleWm = getScale(source, watermark, scale)
    // now create the matrix
    matrix = Matrix()
    matrix.postScale(scaleWm, scaleWm)
    // Determine the post-scaled size of the watermark
    rectF = RectF(0f, 0f, watermark.width.toFloat(), watermark.height.toFloat())
    matrix.mapRect(rectF)

    // below method will decide the position of the logo on the image
    //for right bottom corner use below line

    // matrix.postTranslate(width - rectF.width(), height - rectF.height());

    // i am going to add it my logo at the top left corner
    matrix.postTranslate(dx, dy)


    // set alpha/opacity of paint which is going to draw watermark
    paint.alpha = alpha
    // now draw the watermark on the canvas
    canvas.drawBitmap(watermark, matrix, paint)

    //cleaning up the memory
    watermark.recycle()

    // now return the watermarked image to the calling location
    return bitmap
}
