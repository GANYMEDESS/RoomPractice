package com.bae.roompractice.application

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.bae.roompractice.BuildConfig.DEBUG

class BaeApplication: Application()
{
    companion object{
        val debug = DEBUG
    }

    override fun onCreate() {
        super.onCreate()
        applicationContext?.let {
            if (DEBUG) {
                isDebuggable(it)
            }
        }
    }

    /**
     * @param context
     * @return debuggable
     */
    private fun isDebuggable(context: Context): Boolean {
        var debuggable = false

        try {
            val appInfo: ApplicationInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
            debuggable = (0 != (appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE))
        } catch (e: PackageManager.NameNotFoundException) {}

        return debuggable
    }
}