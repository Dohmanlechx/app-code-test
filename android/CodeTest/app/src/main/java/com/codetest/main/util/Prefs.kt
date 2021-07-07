package com.codetest.main.util

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import androidx.preference.PreferenceManager
import java.util.*

class Prefs(private val context: Context) {
    companion object {
        const val KEY = "api_key"
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun preferences(): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun apiKey(): String {
        preferences().getString(KEY, null)?.let {
            return it
        } ?: kotlin.run {
            return createNewApiKeyAndStore(preferences())
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun createNewApiKeyAndStore(prefs: SharedPreferences): String {
        val apiKey = UUID.randomUUID().toString()
        prefs.edit().putString(KEY, apiKey).apply()
        return apiKey
    }
}