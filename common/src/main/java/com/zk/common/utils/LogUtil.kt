package com.zk.common.utils

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream

open class LogUtil {
    var logSwitch = false;
    private val logTag = "TEST";
    private var logPath: String? = null

    companion object {
        val instance: LogUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LogUtil()
        }
    }

    fun init(context: Context) {
        val externalFilesDir = context.getExternalFilesDir(null)
        if (externalFilesDir != null) {
            val file = File("${externalFilesDir.path}/Log")
            if (!file.exists()) {
                try {
                    file.mkdirs()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            logPath = file.path + "/" + TimeUtil.nowTimeOfDay() + ".txt"
        }
    }

    /**
     * @param msg          byte[]信息
     * @param msgLength    需要打印的msg的长度
     */
    @Deprecated(message = "已经弃用，刚开始写kotlin的时候，用Java的思路写的，不好意思，弃用了！", replaceWith = ReplaceWith(expression = "d(tag = logTag, msg = msg, recordLocal = false)"))
    fun d(msg: ByteArray, msgLength: Int = msg.size) {
        d(msg = msg, msgLength = msgLength, recordLocal = false)
    }

    /**
     * @param tag          标签
     * @param msg          byte[]信息
     * @param msgLength    需要打印的msg的长度
     */
    @Deprecated(message = "已经弃用，刚开始写kotlin的时候，用Java的思路写的，不好意思，弃用了！", replaceWith = ReplaceWith(expression = "d(tag = logTag, msg = msg, recordLocal = false)"))
    fun d(tag: String = logTag, msg: ByteArray, msgLength: Int = msg.size) {
        d(tag = tag, msg = msg, msgLength = msgLength, recordLocal = false)
    }

    /**
     * @param tag          标签
     * @param msg          byte[]信息
     * @param msgLength    需要打印的msg的长度
     * @param recordLocal  是否本地日志保存
     */
    fun d(tag: String = logTag, msg: ByteArray, msgLength: Int = msg.size, recordLocal: Boolean = false) {
        if (logSwitch && msgLength <= msg.size) {
            val buffers = StringBuilder()
            for (i in 0 until msgLength) {
                buffers.append("${Integer.toHexString(msg[i].toInt() and 0xFF)}  ")
            }
            d(tag, buffers.toString(), recordLocal)
        }
    }

    /**
     * @param tag          标签
     * @param msg          信息
     * @param recordLocal  是否本地日志保存
     */
    fun d(tag: String = logTag, msg: String, recordLocal: Boolean = false) {
        if (logSwitch) {
            Log.d(tag, "${targetStackTraceElementMessage()}---$msg")
            if (recordLocal && logPath != null) {
                // 本地日志保存
                try {
                    val c = ByteArray(2)
                    c[0] = 0x0d
                    c[1] = 0x0a// 用于输入换行符的字节码
                    val t = String(c);// 将该字节码转化为字符串类型
                    val fileOutputStream = FileOutputStream(logPath, true)
                    val charset = Charsets.UTF_8
                    val dataBytes = ("[${TimeUtil.nowTimeOfSeconds()}]\t$tag  ---  ${targetStackTraceElementMessage()}" +
                            "  ---  $msg").toByteArray(charset)
                    fileOutputStream.write(dataBytes)
                    fileOutputStream.write(t.toByteArray(charset))
                    fileOutputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun targetStackTraceElementMessage(): String {
        val stackTraceElement = targetStackTraceElement()
        return "(${stackTraceElement?.fileName}:${stackTraceElement?.lineNumber}) ------ "
    }

    private fun targetStackTraceElement(): StackTraceElement? {
        var targetStackTrace: StackTraceElement? = null
        var shouldTrace = false
        val stackTrace = Thread.currentThread().stackTrace
        for (stackTraceElement in stackTrace) {
            val isLogMethod = stackTraceElement.className == LogUtil::class.java.name;
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement
                break
            }
            shouldTrace = isLogMethod
        }
        return targetStackTrace
    }
}