package com.bae.roompractice.utils

import android.util.Log
import com.bae.roompractice.application.BaeApplication

object SimpleLog
{
    /**
     * Log Level Error
     */
    fun e(message: String) {
        if (BaeApplication.debug) Log.e(Constants.TAG, buildLogMsg(message))
    }

    /**
     * Log Level Warning
     */
    fun w(message: String) {
        if (BaeApplication.debug) Log.w(Constants.TAG, buildLogMsg(message))
    }

    /**
     * Log Level Information
     */
    fun i(message: String) {
        if (BaeApplication.debug) Log.i(Constants.TAG, buildLogMsg(message))
    }

    /**
     * Log Level Debug
     */
    fun d(message: String) {
        if (BaeApplication.debug) Log.d(Constants.TAG, buildLogMsg(message))
    }

    /**
     * Log Level Verbose
     */
    fun v(message: String) {
        if (BaeApplication.debug) Log.v(Constants.TAG, buildLogMsg(message))
    }


    private fun buildLogMsg(message: String): String {

        val ste = Thread.currentThread().stackTrace[4]

        val sb = StringBuilder()

        sb.append("[")
        sb.append(ste.fileName.replace(".java", ""))
        sb.append("::")
        sb.append(ste.methodName)
        sb.append("]")
        sb.append(message)

        return sb.toString()

    }
}