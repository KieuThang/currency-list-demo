package com.hometest.currencylistdemo.common

import android.util.Log
import java.util.*

object AppLog {
    var ASSERT = false
    /**
     * Customize the log tag for your application, so that other apps
     * using Volley don't mix their logs with yours. <br></br>
     * Enable the log property for your tag before starting your app: <br></br>
     * `adb shell setprop log.tag.&lt;tag&gt;`
     */
    fun setTag(tag: String?) {
        d("Changing log tag to %s", tag)
    }

    fun v(format: String?, args: String?) {
        if (AppConstants.DEBUG_MODE) {
            val message = buildMessage(args)
            Log.v(AppConstants.TAG, message)
        }
    }

    fun d(args: String?) {
        if (AppConstants.DEBUG_MODE) {
            val message = buildMessage(args)
            Log.d(AppConstants.TAG, message)
        }
    }

    @JvmStatic
    fun d(tag: String?, args: String?) {
        if (AppConstants.DEBUG_MODE) {
            val message = buildMessage(args)
            Log.d(tag, message)
        }
    }

    fun e(tag: String?, args: String?) {
        if (AppConstants.DEBUG_MODE) {
            val message = buildMessage(args)
            Log.e(tag, message)
        }
    }

    fun e(args: String?) {
        if (AppConstants.DEBUG_MODE) {
            val message = buildMessage(args)
            Log.e(AppConstants.TAG, message)
        }
    }

    fun e(tr: Throwable?, tag: String?, args: String?) {
        if (AppConstants.DEBUG_MODE) {
            val message = buildMessage(args)
            Log.e(tag, message, tr)
        }
    }

    fun asserting(ex: Exception?, message: String?) {
        if (AppConstants.DEBUG_MODE) {
            val msg = buildMessage(message)
            var testEx = ex
            if (testEx == null) testEx = Exception(message)
            e(testEx.toString(), msg)
        }
        check(!ASSERT) { message!! }
    }

    /**
     * Formats the caller's provided message and prepends useful info like
     * calling thread ID and method name.
     */
    private fun buildMessage(args: String?): String {
        val trace =
            Throwable().fillInStackTrace().stackTrace
        var caller = "<unknown>"
        for (i in 2 until trace.size) {
            val clazz: Class<*> = trace[i].javaClass
            if (clazz != AppLog::class.java) {
                var callingClass = trace[i].className
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1)
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1)
                caller = callingClass + "." + trace[i].methodName
                break
            }
        }
        return String.format(
            Locale.US,
            "[%d] %s: %s",
            Thread.currentThread().id,
            caller,
            args
        )
    }
}