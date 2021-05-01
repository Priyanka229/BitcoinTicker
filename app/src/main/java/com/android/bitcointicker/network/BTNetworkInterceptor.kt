package com.android.bitcointicker.network

import com.android.bitcointicker.BitcoinTickerApp
import com.android.bitcointicker.R
import com.android.bitcointicker.network.BTNetworkConnector.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class BTNetworkInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        if (!isNetworkAvailable(BitcoinTickerApp.instance)) {
            throw NoNetworkException()
        }
        return chain.proceed(request)
    }

    class NoNetworkException : IOException() {
        override val message: String
            get() = BitcoinTickerApp.instance.resources.getString(R.string.network_unavailable_message)
    }
}