package com.android.bitcointicker.bitcoin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.android.bitcointicker.R
import com.android.bitcointicker.beans.BitcoinTickerInfo
import com.android.bitcointicker.databinding.ActivityBitcoinBinding
import com.android.bitcointicker.utilities.BTUtility

class BitcoinActivity : AppCompatActivity() {
    private lateinit var viewModel: BitcoinVM

    private val binding: ActivityBitcoinBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_bitcoin) }

    private val bitcoinAdapter: BitCoinAdapter by lazy {
        BitCoinAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** setup UI */
        setUpUI()

        /** view model */
        viewModel = ViewModelProvider(this).get(BitcoinVM::class.java)

        /** set up observers */
        setUpObservers()

        /** notify view-model about the app launch */
        viewModel.resume()
    }

    private fun setUpUI() {
        binding.apply {
            viewPager.adapter = bitcoinAdapter
        }
    }

    private fun setUpObservers() {
        /** open list fragment observer */
        viewModel.bitcoinListLiveData.observe(this) {
            bitcoinAdapter.submitList(it)
        }

        /** loader observer */
        viewModel.uiBlockingLoaderLiveEvent.observe(this) { show ->
            binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        }

        /** show toast */
        viewModel.showMsgLiveEvent.observe(this) { msg ->
            if (msg != null && msg.isNotBlank()) {
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    private inner class BitCoinAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
        var currencyList: List<BitcoinTickerInfo>? = null
        var currentTimeStr: String? = null

        override fun getItemCount() = currencyList?.size ?: 0

        override fun createFragment(position: Int) = BitcoinFragment.getInstance(currencyList!![position], currentTimeStr)

        override fun onBindViewHolder(holder: FragmentViewHolder, position: Int, payloads: MutableList<Any>) {
//            val bitcoinFragment = supportFragmentManager.findFragmentById(holder.itemId.toInt()) as? BitcoinFragment
            val bitcoinFragment = supportFragmentManager.findFragmentByTag("f$position") as? BitcoinFragment
            if (bitcoinFragment != null) {
                bitcoinFragment.inflateView(currencyList!![position], currentTimeStr)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }

        fun submitList(currencyList: List<BitcoinTickerInfo>?) {
            this.currencyList = currencyList
            this.currentTimeStr = BTUtility.getCurrentTime()
            notifyDataSetChanged()
        }
    }
}