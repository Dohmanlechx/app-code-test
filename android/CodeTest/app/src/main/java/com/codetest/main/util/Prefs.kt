package com.codetest.main.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import java.util.*

class Prefs(private val context: Context) {
    private val key = "api_key"

    private fun preferences(): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun getKey(): String {
        preferences().getString(key, null)?.let {
            return it
        } ?: kotlin.run {
            val apiKey = UUID.randomUUID().toString()
            preferences().edit().putString(key, apiKey).apply()
            return apiKey
        }
    }
}