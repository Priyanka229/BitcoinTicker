package com.android.bitcointicker

import android.app.Application

class BitcoinTickerApp: Application() {
    companion object{
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}