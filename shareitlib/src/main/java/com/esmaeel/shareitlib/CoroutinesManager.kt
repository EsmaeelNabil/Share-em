package com.esmaeel.shareitlib

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object CoroutinesManager {

    fun onMainThread(givenFunction: suspend (() -> Unit)) {
        CoroutineScope(Dispatchers.Main).launch {
            givenFunction()
        }
    }

    fun onIOThread(givenFunction: suspend (() -> Unit)) {
        CoroutineScope(Dispatchers.IO).launch {
            givenFunction()
        }
    }

}