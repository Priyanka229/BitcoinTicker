package com.android.bitcointicker.bitcoin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.bitcointicker.beans.BitcoinTickerInfo
import com.android.bitcointicker.databinding.FragmentBitcoinBinding
import com.android.bitcointicker.utilities.BTUtility

class BitcoinFragment: Fragment() {
    private lateinit var binding: FragmentBitcoinBinding

    companion object {
        private const val INFO_STR = "info_str"
        private const val CURRENT_TIME_STR = "current_time_str"

        fun getInstance(bitcoinTickerInfo: BitcoinTickerInfo, currentTimeStr: String?) = BitcoinFragment().apply {
            arguments = Bundle().apply {
                putParcelable(INFO_STR, bitcoinTickerInfo)
                putString(CURRENT_TIME_STR,  currentTimeStr)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBitcoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bitcoinTickerInfo = arguments?.getParcelable<BitcoinTickerInfo>(INFO_STR)
        val currentTimeStr = arguments?.getString(CURRENT_TIME_STR)
        inflateView(bitcoinTickerInfo, currentTimeStr)
    }

    fun inflateView(bitcoinTickerInfo: BitcoinTickerInfo?, currentTimeStr: String?) {
        binding.apply {
            /** time */
            timeTv.text = currentTimeStr

            /** current currency */
            val currencyStr = "${bitcoinTickerInfo?.bitcoinInfo?.symbol}${bitcoinTickerInfo?.bitcoinInfo?.last}"
            currencyTv.text = currencyStr

            /** current type */
            val currencyTypeStr = "BTC/${bitcoinTickerInfo?.currency}"
            currencyTypeTv.text = currencyTypeStr
        }
    }
}