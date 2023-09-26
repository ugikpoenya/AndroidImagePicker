package com.ugikpoenya.sampleappimagepicker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ugikpoenya.imagepicker.ImageEraserActivity
import com.ugikpoenya.imagepicker.ImageHolder
import com.ugikpoenya.imagepicker.ImagePicker
import com.ugikpoenya.imagepicker.currentPhotoFile
import com.ugikpoenya.imagepicker.tools.addWatermarkBitmap
import com.ugikpoenya.imagepicker.tools.getBitmabFromUri
import com.ugikpoenya.imagepicker.tools.initTempUriFile
import com.ugikpoenya.sampleappimagepicker.databinding.ActivityMainBinding
import com.yalantis.ucrop.UCrop
import java.io.ByteArrayOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    var currentBitmab: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.btnCamera?.setOnClickListener {
            cameraLauncer.launch(ImagePicker().getIntentCamera(this))
        }

        binding?.btnGallery?.setOnClickListener {
            galeryLauncer.launch(ImagePicker().getIntentGallery())
        }


    }


    private var galeryLauncer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            showCroppImage(result.data?.data)
        }
    }

    private var cameraLauncer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            showCroppImage(Uri.fromFile(currentPhotoFile))
        }
    }

    private fun showCroppImage(imageUri: Uri?) {
        val options = UCrop.Options()
        options.setCompressionFormat(Bitmap.CompressFormat.PNG)
        options.setRootViewBackgroundColor(ContextCompat.getColor(this, R.color.white))
        if (imageUri != null) {
            val cropIntent = UCrop.of(imageUri, initTempUriFile(this))
                .withOptions(options)
                .getIntent(this)
            cropImage.launch(cropIntent)
        }
    }

    private val cropImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.d("LOG", "cropImage Result")
            val uri = UCrop.getOutput(result.data!!)
            currentBitmab = getBitmabFromUri(this, uri)
            binding?.imageView?.setImageBitmap(currentBitmab)
            showImageEraser()
        }
    }

    fun showImageEraser() {
        if (currentBitmab != null) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            currentBitmab?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            try {
                byteArrayOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            ImageHolder.setData(byteArray)
            val intent = Intent(applicationContext, ImageEraserActivity::class.java)
            eraserLauncer.launch(intent)
        }
    }

    private val eraserLauncer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.d("LOG", "eraserImage Result")
            currentBitmab = BitmapFactory.decodeByteArray(ImageHolder.getData(), 0, ImageHolder.getData().size)

            val watermark = BitmapFactory.decodeResource(resources, R.drawable.icon)
            val btmp = addWatermarkBitmap(currentBitmab!!, watermark, 0.2f, 100, 100f)
            binding?.imageView?.setImageBitmap(btmp)
        }
    }
}