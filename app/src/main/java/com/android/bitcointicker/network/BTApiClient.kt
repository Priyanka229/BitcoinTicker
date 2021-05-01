package com.android.bitcointicker.network

import com.android.bitcointicker.utilities.BTLogger
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

object BTApiClient {
    var headers =  ConcurrentHashMap<String, String>()

    private val httpClient =
            OkHttpClient.Builder()
                    .addInterceptor(Interceptor { chain ->
                        val original = chain.request()

                        val request = original.newBuilder()
                            .headers(getHeaders(headers))
                            .method(original.method, original.body)
                            .build()
                        chain.proceed(request)
                    })
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(BTNetworkInterceptor())
                    .apply {
                        if (BTLogger.DEBUG) addInterceptor(
                            BTNetworkLoggingIntercepter()
                        )
                    }
                    .retryOnConnectionFailure(true)
                    .build()

    private val retrofit  = Retrofit.Builder()
            .addConverterFactory(BTNullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BTNetworkConstants.URL)
            .client(httpClient)
            .build()

    var service: BTApiService = retrofit.create(BTApiService::class.java)

    private fun getHeaders(map: ConcurrentHashMap<String, String>): Headers {
        val builder = Headers.Builder()
        for ((key, value) in map) {
            builder.add(key, value)
        }
        return builder.build()
    }
}