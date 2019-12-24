package com.orange.jzchi.jzframework

import android.app.Application
import android.content.Intent
import android.util.Log

abstract class RootApp : Application() {
    private val restartHandler = Thread.UncaughtExceptionHandler { thread, ex ->
        Log.e("error", ex.message)
        restartApp()
    }

    internal var cls: Class<*>? = null
    override fun onCreate() {
        super.onCreate()
        AppInit()
    }



    fun SetCrashRestart(cls: Class<*>):RootApp{
        Thread.setDefaultUncaughtExceptionHandler(restartHandler)
        return this
    }

    fun restartApp() {
        val intent = Intent(applicationContext, cls)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        android.os.Process.killProcess(android.os.Process.myPid())
    }


    //載入app
    abstract fun AppInit()
}