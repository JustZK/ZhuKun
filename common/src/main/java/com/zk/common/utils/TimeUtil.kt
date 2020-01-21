package com.zk.common.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    fun nowTimeOfSeconds(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        return formatter.format(Date())
    }

    fun nowTimeOfDay(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        return formatter.format(Date())
    }
}