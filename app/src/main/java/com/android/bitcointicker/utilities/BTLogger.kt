package com.android.bitcointicker.utilities

import android.util.Log
import androidx.databinding.library.BuildConfig

object BTLogger {

    private const val TAG = "BTLogger"
    val DEBUG = BuildConfig.DEBUG

    fun d(tag: String = TAG, msg: String) {
        if (DEBUG) {
            Log.d(tag, msg)
        }
    }
}