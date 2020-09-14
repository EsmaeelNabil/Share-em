package com.esmaeel.shareitlib

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.util.*

/**
 * saves the image and return a URI for it
 */
fun Context.getUriFromBitmap(bitmap: Bitmap): Uri {
    // save the image and get it's uri
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
    val path: String = MediaStore.Images.Media.insertImage(
        this.contentResolver,
        bitmap,
        "${Calendar.getInstance().time}_image",
        "image ${Calendar.getInstance().time}"
    )
    return Uri.parse(path)
}
