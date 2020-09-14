package com.esmaeel.shareit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.esmaeel.shareit.databinding.ActivityMainBinding
import com.esmaeel.shareitlib.SharableItem
import com.esmaeel.shareitlib.Share

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*Share.with(context = this)
            .item(
                SharableItem(pictureUrl = "ImageUrl"),
                onStart = {},
                onFinish = { isSuccessful, errorMessage -> })

        Share.with(context = this)
            .item(
                SharableItem(data = "Text To Share"),
                onStart = {},
                onFinish = { isSuccessful, errorMessage -> })*/


        binding.share.setOnClickListener {

            Share.with(context = this)
                .item(SharableItem(
                    pictureUrl = "https://images.unsplash.com/photo-1554290712-e640351074bd?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                    data = "this is an example for sharing image with Text with AppLink, and a custom message for sharing link :)",
                    shareAppLink = true,
                    downloadOurAppMessage = "Find us here"
                ),
                    onStart = {
                        // do something onStart like : Loading
                        Log.e(TAG, "onStart Sharing")
                        Toast.makeText(applicationContext, "onStart Sharing", Toast.LENGTH_SHORT)
                            .show()
                    },
                    onFinish = { isSuccessful: Boolean, errorMessage: String ->
                        // if isSuccessful you will see an intent chooser else check the error message
                        if (isSuccessful) {
                            Log.e(TAG, "Successfully shared")
                        } else {
                            Log.e(TAG, "error happened : $errorMessage")
                        }
                    }
                )
        }

    }


}