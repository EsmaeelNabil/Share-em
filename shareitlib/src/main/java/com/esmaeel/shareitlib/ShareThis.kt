package com.esmaeel.shareitlib

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.StrictMode
import coil.Coil
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult


public class Share private constructor(private var context: Context) {
    private lateinit var imageLoader: ImageLoader

    /**
     * provides a static object
     */
    companion object {
        fun with(context: Context): Share {
            return Share(context)
        }
    }

    /**
     * determines if the user need to share an image with text
     * or only text
     */
    fun item(
        itemSpecs: SharableItem,
        onStart: () -> Unit,
        onFinish: (successful: Boolean, error: String) -> Unit
    ) {
        imageLoader = Coil.imageLoader(context)
        if (itemSpecs.pictureUrl.isNotEmpty() && android.util.Patterns.WEB_URL.matcher(itemSpecs.pictureUrl)
                .matches()
        ) {
            shareItemWithImage(
                itemSpecs,
                onStart,
                onFinish
            )
        } else shareItem(itemSpecs, onStart, onFinish)
    }


    /**
     * share only text
     */
    private fun shareItem(
        itemSpecs: SharableItem,
        onStart: () -> Unit,
        onFinish: (successful: Boolean, error: String) -> Unit
    ) = igniteTextIntent(onStart, context, itemSpecs, onFinish)


    /**
     * sends a normal text to intent chooser
     * with notifying the observers
     */
    private fun igniteTextIntent(
        onStart: () -> Unit,
        context: Context,
        itemSpecs: SharableItem,
        onFinish: (successful: Boolean, error: String) -> Unit
    ) {
        onStart.invoke()
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"

        if (itemSpecs.shareAppLink) {
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "${itemSpecs.data} \nYou can download our app from \nhttps://play.google.com/store/apps/details?id=${context.packageName}"
            );
        }
        intent.putExtra(android.content.Intent.EXTRA_TEXT, itemSpecs.data);
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(Intent.createChooser(intent, "Share"))
        onFinish(true, "")
    }


    /**
     * Downloads the image (asynchronously)
     */
    private fun shareItemWithImage(
        itemSpecs: SharableItem,
        onStart: () -> Unit,
        onFinish: (successful: Boolean, error: String) -> Unit
    ) {

        onStart.invoke()

        CoroutinesManager.onIOThread {
            val request = ImageRequest.Builder(context)
                .data(itemSpecs.pictureUrl)
                .allowHardware(false)
                .build()
            val response = imageLoader.execute(request)

            // network might not be working or slow, so an exception might been caught
            // so we check for if the drawable is null or not
            if (response.drawable != null) {
                val result = (response as SuccessResult).drawable
                val bitmap = (result as BitmapDrawable).bitmap
                CoroutinesManager.onMainThread {
                    igniteImageIntent(context, itemSpecs, bitmap, onFinish)
                }
            } else {
                // the exception has been caught so we cast the response to
                // ErrorResult that has the throwable of what happened
                val result = (response as ErrorResult).throwable
                CoroutinesManager.onMainThread {
                    onFinish(false, result.message ?: "Unknown Error")
                }
            }

        }

    }


    /**
     * saves the bitmap in the phone gallery.
     * gets the URI for tha image
     * sends an image URI with the image data to the intent chooser
     * also generate a playStore link from the package name of the passed Context
     * 4- notifying the observers
     */
    private fun igniteImageIntent(
        context: Context,
        itemSpecs: SharableItem,
        bitmap: Bitmap,
        onFinish: (successful: Boolean, error: String) -> Unit
    ) {
        try {

            // generate a policy for this fun
            StrictMode.setVmPolicy(getSharingPolicy())

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"

            // save the bitmap and generate a URI for it
            intent.putExtra(Intent.EXTRA_STREAM, context.getUriFromBitmap(bitmap))

            // if the developer chosen to share the playStore link with the SharableItem() params
            if (itemSpecs.shareAppLink) {
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "${itemSpecs.data} \nYou can download our app from \nhttps://play.google.com/store/apps/details?id=${context.packageName}"
                );
            }

            // grant the chooser to read the image from the uri and start sharing
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(intent, "Share Image"))
            // successful sharing
            onFinish(true, "")

        } catch (e: Exception) {
            // an Error has happened abort with error message.
            onFinish(false, e.localizedMessage ?: "Unknown Error!")
        }

    }


    /**
     * set a sharing policy for a fun
     */
    private fun getSharingPolicy(): StrictMode.VmPolicy? {
        return StrictMode.VmPolicy.Builder().build();
    }

}


