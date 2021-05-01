package com.android.bitcointicker.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.bitcointicker.utilities.SingleLiveEvent
import com.android.bitcointicker.utilities.BTLogger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

abstract class BaseBTVM<REPO: BaseBTRepository, INTER: BaseBTInteractor>(application: Application): AndroidViewModel(application) {
    lateinit var repository: REPO
    lateinit var interactor: INTER

    val uiBlockingLoaderLiveEvent = SingleLiveEvent<Boolean>() /** ui-blocking loader live-data */
    val showMsgLiveEvent = SingleLiveEvent<String>() /** msg live-data */

    protected val coroutineContext = Dispatchers.Default + CoroutineExceptionHandler{ _, th ->
        if (BTLogger.DEBUG) { th.printStackTrace() }
    }
}