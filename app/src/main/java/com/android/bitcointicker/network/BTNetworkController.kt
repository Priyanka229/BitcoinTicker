package com.android.bitcointicker.network

import com.android.bitcointicker.BitcoinTickerApp
import com.android.bitcointicker.R
import com.android.bitcointicker.utilities.BTUtility
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object BTNetworkController {
    suspend inline fun <reified T> doGet(api: String, queryMap: Map<String, Any> = HashMap()): BTResponse<T> {
        return try {
            val apiResponse = BTApiClient.service.get<LinkedTreeMap<String, String>>(api, queryMap)
            handleApiResponse(api, apiResponse)
        } catch (ex: Exception) {
            val error = handleApiCallExceptions(ex)
            BTResponse(requestType = api, error = error)
        }
    }

    inline fun <reified T> handleApiResponse(requestType: String, response: Response<LinkedTreeMap<String, String>>): BTResponse<T> {
        val returnValue: BTResponse<T>

        when {
            response.isSuccessful -> {
                val gsonStr = Gson().toJson(response.body())
                val responsePOJO = BTUtility.toParse<T>(gsonStr)
                returnValue = BTResponse(requestType = requestType, data = responsePOJO)
            }

            else -> {
                var error: BTError? = null
                val errorString = if (response.errorBody() != null) response.errorBody()!!.string() else null
                if (errorString != null) {
                    error = Gson().fromJson(errorString, BTError::class.java)
                }

                if (error == null) { error = BTError(msg = BitcoinTickerApp.instance.resources.getString(
                    R.string.something_went_wrong)) }

                returnValue = BTResponse(requestType = requestType, error = error)
            }
        }

        return returnValue
    }

    fun handleApiCallExceptions(ex: Exception): BTError {
        return when(ex) {
            is BTNetworkInterceptor.NoNetworkException -> BTError(msg = BitcoinTickerApp.instance.resources.getString(R.string.network_unavailable_message))
            is JsonParseException -> BTError(msg = BitcoinTickerApp.instance.resources.getString(R.string.parse_error))
            is SocketTimeoutException -> BTError(msg = BitcoinTickerApp.instance.resources.getString(R.string.socket_time_out))
            is UnknownHostException -> BTError(msg = BitcoinTickerApp.instance.resources.getString(R.string.something_went_wrong))
            else -> throw ex
        }
    }
}

data class BTResponse <T> (
    val requestType: String,
    val data: T? = null,
    val error: BTError? = null
)
data class BTError(
    val msg: String
)
