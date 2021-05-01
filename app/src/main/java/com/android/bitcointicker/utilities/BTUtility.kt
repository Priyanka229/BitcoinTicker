package com.android.bitcointicker.utilities

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

object BTUtility {

    inline fun <reified T> toParse(jsonString: String?): T? {
        return try {
            Gson().fromJson<T>(jsonString, object : TypeToken<T?>() {}.type)
        } catch (ex: Exception) {
            null
        }
    }

    fun getCurrentTime(): String? {
        return try {
            val sdf = SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.getDefault())
            sdf.format(Date())
        } catch (ex: Exception) {
            null
        }
    }
}