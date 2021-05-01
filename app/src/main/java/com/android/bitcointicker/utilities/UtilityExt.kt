package com.android.bitcointicker.utilities

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

inline fun <reified T> JsonObject.fetch(key: String): T? {
    return try {
        var returnValue: T? = null
        if (has(key)) {
            returnValue = Gson().fromJson(get(key), object : TypeToken<T?>() {}.type)
        }
        returnValue
    } catch (ex: Exception) {
        null
    }
}