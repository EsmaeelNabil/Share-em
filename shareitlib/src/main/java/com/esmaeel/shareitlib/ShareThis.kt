package com.esmaeel.shareitlib

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.StrictMode

public class Share private constructor(private var context: Context) {

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

        // put the combination in the intent Extras
        intent.putExtra(Intent.EXTRA_TEXT, generateSharedText(itemSpecs = itemSpecs));

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(Intent.createChooser(intent, "Share"))
        onFinish(true, "")
    }

    private fun shareItemWithImage(
        itemSpecs: SharableItem,
        onStart: () -> Unit,
        onFinish: (successful: Boolean, error: String) -> Unit
    ) {
        onStart.invoke()
        context.getUriFromImageUrl(url = itemSpecs.pictureUrl, onFail = {
            if (itemSpecs.failOnDownloadFailing)
                onFinish(false, it.message ?: "Unknown error while downloading image")
            else igniteTextIntent(onStart, context, itemSpecs, onFinish)
        }, onUriReady = {
            igniteImageIntent(context, itemSpecs, it, onFinish)
        })
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
        uri: Uri? = null,
        onFinish: (successful: Boolean, error: String) -> Unit
    ) {
        try {

            // generate a policy for this fun
            StrictMode.setVmPolicy(getSharingPolicy())

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"

            // save the bitmap and generate a URI for it
            uri?.let {
                intent.putExtra(Intent.EXTRA_STREAM, it)
            }

            // put the combination in the intent Extras
            intent.putExtra(Intent.EXTRA_TEXT, generateSharedText(itemSpecs = itemSpecs));

            // grant the chooser to read the image from the uri and start sharing
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(intent, "Share"))
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

    private fun generateSharedText(itemSpecs: SharableItem): String {
        // if has text data
        var sharedText = if (itemSpecs.data.isNotEmpty()) itemSpecs.data else ""
        // if has shareAppLink enabled
        sharedText += if (itemSpecs.shareAppLink) "\n ${if (itemSpecs.downloadOurAppMessage.isNotEmpty()) itemSpecs.downloadOurAppMessage else "You can download our app from"} https://play.google.com/store/apps/details?id=${context.packageName}" else ""
        return sharedText
    }

}


