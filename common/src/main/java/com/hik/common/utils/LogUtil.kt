package com.hik.common.utils

import android.util.Log

class LogUtil {
    var logSwitch = true;
    private val logTag = "TEST";

    companion object {
        val instance: LogUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LogUtil()
        }
    }

    fun d(msg: String) {
        d(null, msg, false)
    }

    fun d(tag: String, msg: String) {
        d(tag, msg, false)
    }

    fun d(tag: String?, msg: String, recordLocal: Boolean) {
        if (logSwitch) {
            Log.d(tag ?: logTag, msg)
            if (recordLocal) {
                //TODO
            }
        }
    }
}