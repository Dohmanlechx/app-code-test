package com.codetest

import android.app.Application
import android.content.Context
import com.codetest.main.koin.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

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

        startKoin {
            androidLogger()
            androidContext(this@CodeTestApplication)
            modules(appModules())
        }
    }
}
