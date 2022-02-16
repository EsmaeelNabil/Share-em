package com.esmaeel.shareitlib

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.ImageHeaderParser
import com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser
import com.bumptech.glide.request.target.Target
import java.io.FileInputStream

class ShareFileProvider : FileProvider() {
    private val mImageHeaderParser: ImageHeaderParser = DefaultImageHeaderParser()

    override fun getType(uri: Uri): String? {
        var type = super.getType(uri)
        if (!TextUtils.equals(type, "application/octet-stream")) {
            return type;
        }
        try {
            val parcelFileDescriptor = openFile(uri, "r") ?: return type
            parcelFileDescriptor.use { parcelFileDescriptor ->
                val fileInputStream = FileInputStream(
                    parcelFileDescriptor.fileDescriptor
                )
                fileInputStream.use { fileInputStream ->
                    val imageType: ImageHeaderParser.ImageType = mImageHeaderParser.getType(
                        fileInputStream
                    )
                    type = getTypeFromImageType(imageType, type ?: "image/jpeg")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return type
    }


    private fun getTypeFromImageType(
        imageType: ImageHeaderParser.ImageType,
        defaultType: String
    ): String? {
        val extension = when (imageType) {
            ImageHeaderParser.ImageType.GIF -> "gif"
            ImageHeaderParser.ImageType.JPEG -> "jpg"
            ImageHeaderParser.ImageType.PNG_A, ImageHeaderParser.ImageType.PNG -> "png"
            ImageHeaderParser.ImageType.WEBP_A, ImageHeaderParser.ImageType.WEBP -> "webp"
            else -> return defaultType
        }
        // See FileProvider.getType(Uri)
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
}

private fun Context.getAuthority() = "${this.packageName}.library.file.provider"

fun Context.getUriFromImageUrl(
    url: String,
    onFail: (error: Throwable) -> Unit,
    onUriReady: (uri: Uri) -> Unit
) {
    CoroutinesManager.onIOThread {
        try {
            val image = Glide.with(this)
                .downloadOnly()
                .load(url)
                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .get()
            val uri = FileProvider.getUriForFile(this, this.getAuthority(), image)
            CoroutinesManager.onMainThread { onUriReady(uri) }
        } catch (e: Throwable) {
            onFail(e)
        }
    }

}