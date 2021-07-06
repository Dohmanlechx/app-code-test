package com.codetest

import android.app.Application
import android.content.Context

class CodeTestApplication : Application() {
    companion object {
        var application: Application? = null
            private set

        val context: Context
            get() = application!!.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}
