package com.ugikpoenya.imagepicker

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RandomImage {
    fun getImage(context: Context, function: (response: String?) -> (Unit)) {
        getImage(context, 512, 512, function)
    }

    fun getImage(context: Context, width: Int, height: Int, function: (response: String?) -> (Unit)) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://random.imagecdn.app/v1/image?width=$width&height=$height"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                function(response)
            },
            { e ->
                Log.d("LOG", e.message.toString())
                function(null)
            })
        queue.add(stringRequest)
    }

    fun getImage(width: Int, height: Int): String {
        return "https://random.imagecdn.app/$width/$height"
    }

    fun getImage(): String {
        return getImage(512, 512)
    }
}