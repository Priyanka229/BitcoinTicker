package com.android.bitcointicker.beans

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BitcoinPOJO(
    @SerializedName("USD") val usd: BitcoinInfo? = null,
    @SerializedName("AUD") val aud: BitcoinInfo? = null,
    @SerializedName("BRL") val brl: BitcoinInfo? = null
): Parcelable

@Parcelize
data class BitcoinInfo(
    @SerializedName("15m") val duration: String? = null,
    @SerializedName("last") var last: String? = null,
    @SerializedName("buy") val buy: String? = null,
    @SerializedName("sell") val sell: String? = null,
    @SerializedName("symbol") val symbol: String? = null,
): Parcelable

@Parcelize
data class BitcoinTickerInfo(
    val currency: String? = null,
    val bitcoinInfo: BitcoinInfo? = null,
): Parcelable