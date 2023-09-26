package com.ugikpoenya.imagepicker.tools

import android.content.Context
import android.net.Uri

fun initTempUriFile(context: Context): Uri {
    return Uri.fromFile(initTempFile(context))
}