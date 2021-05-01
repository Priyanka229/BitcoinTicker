package com.android.bitcointicker.bitcoin

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.android.bitcointicker.base.BaseBTVM
import com.android.bitcointicker.beans.BitcoinTickerInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BitcoinVM(app: Application) : BaseBTVM<BitcoinRepository, BitcoinInteractor>(app) {
    val bitcoinListLiveData = MediatorLiveData<List<BitcoinTickerInfo>>()

    init {
        repository = BitcoinRepository()
        interactor = BitcoinInteractor()
    }

    /** resume the state */
    fun resume() {
        /** fetch the signed in user response */
        viewModelScope.launch(coroutineContext) {
            /** show loader */
            uiBlockingLoaderLiveEvent.postValue(true)

            fetchBitcoin()
        }
    }

    private suspend fun fetchBitcoin(retryAttempt: Int = 0) {
        /** reading from server */
        val output = repository.getBitcoin()
        if (output.data != null) {
            val bitcoinData = output.data

            val bitcoinList = interactor.getBitcoinTickerInfoList(bitcoinData)

            bitcoinListLiveData.postValue(bitcoinList)

            /** hide loader */
            uiBlockingLoaderLiveEvent.postValue(false)

            delay(1000)
            fetchBitcoin(retryAttempt = 0)
        } else {
            output.error?.msg?.let { showMsgLiveEvent.postValue(it) }

            /** hide loader */
            uiBlockingLoaderLiveEvent.postValue(false)

            if (retryAttempt < BitcoinInteractor.RETRY_ATTEMPT_COUNT) {
                delay(1000)
                fetchBitcoin(retryAttempt = retryAttempt + 1)
            }
        }
    }
}