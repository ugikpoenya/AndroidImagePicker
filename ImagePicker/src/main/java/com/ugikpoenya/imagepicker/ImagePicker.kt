package com.ugikpoenya.imagepicker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.ugikpoenya.imagepicker.tools.initTempFile
import java.io.File


lateinit var currentPhotoFile: File

class ImagePicker {
    fun getIntentGallery(): Intent {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        return Intent.createChooser(intent, "Select Picture")
    }

    fun getIntentCamera(context: Context): Intent {
        currentPhotoFile = initTempFile(context)
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.resolveActivity(context.packageManager)
        val photoURI: Uri = FileProvider.getUriForFile(
            context, context.packageName + ".fileprovider", currentPhotoFile
        )
        return takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
    }
}