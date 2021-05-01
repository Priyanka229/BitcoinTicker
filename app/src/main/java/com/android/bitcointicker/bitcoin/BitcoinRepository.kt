package com.android.bitcointicker.bitcoin

import com.android.bitcointicker.base.BaseBTRepository
import com.android.bitcointicker.network.BTNetworkConstants.BITCOIN
import com.android.bitcointicker.network.BTResponse
import com.google.gson.JsonObject

class BitcoinRepository: BaseBTRepository() {
    suspend fun getBitcoin(): BTResponse<JsonObject> = doGet(BITCOIN)
}