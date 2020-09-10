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


        binding.share.setOnClickListener {
            Share.with(context = this)
                .item(SharableItem(
//                    pictureUrl = "https://images.unsplash.com/photo-1578738095540-cadfa6e5f6c1?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                    pictureUrl = "google",
                    data = "this is a test image!",
                    shareAppLink = false
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
                            Toast.makeText(
                                applicationContext,
                                "Successfully shared",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.e(TAG, "error happened : $errorMessage")
                            Toast.makeText(
                                applicationContext,
                                "error happened : $errorMessage",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
        }

    }


}