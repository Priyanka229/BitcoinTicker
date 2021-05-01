package com.android.bitcointicker.base

import com.android.bitcointicker.network.BTNetworkController
import kotlinx.coroutines.CancellationException

open class BaseBTRepository {
    @Throws(CancellationException::class)
    suspend inline fun <reified T>doGet(api: String, queryMap: Map<String, Any> = HashMap()) = BTNetworkController.doGet<T>(api, queryMap)
}