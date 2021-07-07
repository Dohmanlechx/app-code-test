package com.codetest.main.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.*

class KeyUtil(private val context: Context) {
    companion object {
        const val KEY = "api_key"
    }

    private fun preferences(): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun getKey(): String {
        preferences().getString(KEY, null)?.let {
            return it
        } ?: kotlin.run {
            val apiKey = UUID.randomUUID().toString()
            preferences()
                .edit()
                .putString(KEY, apiKey)
                .apply()
            return apiKey
        }
    }
}