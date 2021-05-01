package com.android.bitcointicker.bitcoin

import com.android.bitcointicker.base.BaseBTInteractor
import com.android.bitcointicker.beans.BitcoinInfo
import com.android.bitcointicker.beans.BitcoinTickerInfo
import com.android.bitcointicker.utilities.fetch
import com.google.gson.JsonObject

class BitcoinInteractor: BaseBTInteractor() {
    companion object {
        const val RETRY_ATTEMPT_COUNT = 20
    }
    fun getBitcoinTickerInfoList(bitcoinData: JsonObject): List<BitcoinTickerInfo> {
        val list = mutableListOf<BitcoinTickerInfo>()

        getBitCoinTickerInfo(bitcoinData, "USD")?.let { list.add(it) }
        getBitCoinTickerInfo(bitcoinData, "CAD")?.let { list.add(it) }
        getBitCoinTickerInfo(bitcoinData, "EUR")?.let { list.add(it) }
        getBitCoinTickerInfo(bitcoinData, "GBP")?.let { list.add(it) }
        getBitCoinTickerInfo(bitcoinData, "INR")?.let { list.add(it) }

        return list
    }

    private fun getBitCoinTickerInfo(bitcoinData: JsonObject?, currency: String): BitcoinTickerInfo? {
        return try {
            val bitcoinInfo = bitcoinData?.fetch<BitcoinInfo?>(currency)
            if (bitcoinInfo != null) {
                BitcoinTickerInfo(currency, bitcoinInfo)
            } else {
                null
            }
        } catch (ex: Exception) {
            null
        }
    }
}