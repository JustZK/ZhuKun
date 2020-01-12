package com.hik.common.utils

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class LogUtil {
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
            val file = File("${externalFilesDir.path}/LOG")
            if (!file.exists()) {
                try {
                    file.mkdirs()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            logPath = file.path + "/" + nowTimeShort() + ".txt"
        }
    }

    /**
     * @param msg          信息
     */
    fun d(msg: String) {
        d(null, msg, false)
    }

    /**
     * @param tag          标签
     * @param msg          信息
     */
    fun d(tag: String, msg: String) {
        d(tag, msg, false)
    }

    /**
     * @param msg          byte[]信息
     * @param msgLength    需要打印的msg的长度
     */
    fun d(msg: ByteArray, msgLength: Int){
        if (logSwitch && msg.size <= msgLength){
            val buffers = StringBuilder()
            for (i in 0..msgLength){
                buffers.append("${Integer.toHexString(msg[i].toInt() and 0xFF)}  ")
            }
            d(null, buffers.toString(), false)
        }
    }

    /**
     * @param tag          标签
     * @param msg          byte[]信息
     * @param msgLength    需要打印的msg的长度
     */
    fun d(tag: String, msg: ByteArray, msgLength: Int){
        if (logSwitch && msg.size <= msgLength){
            val buffers = StringBuilder()
            for (i in 0..msgLength){
                buffers.append("${Integer.toHexString(msg[i].toInt() and 0xFF)}  ")
            }
            d(tag, buffers.toString(), false)
        }
    }

    /**
     * @param tag          标签
     * @param msg          byte[]信息
     * @param msgLength    需要打印的msg的长度
     * @param recordLocal  是否本地日志保存
     */
    fun d(tag: String, msg: ByteArray, msgLength: Int, recordLocal: Boolean){
        if (logSwitch && msg.size <= msgLength){
            val buffers = StringBuilder()
            for (i in 0..msgLength){
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
    fun d(tag: String?, msg: String, recordLocal: Boolean) {
        if (logSwitch) {
            val tagTemp = if (tag == null) logTag else "$logTag  $tag"
            Log.d(tagTemp, "${targetStackTraceElementMessage()}---$msg")
            if (recordLocal && logPath != null) {
                // 本地日志保存
                try {
                    val c = ByteArray(2)
                    c[0] = 0x0d
                    c[1] = 0x0a// 用于输入换行符的字节码
                    val t = String(c);// 将该字节码转化为字符串类型
                    val fileOutputStream = FileOutputStream(logPath, true)
                    val charset = Charsets.UTF_8
                    val dataBytes = ("${nowTime()}\t$tagTemp  ---  ${targetStackTraceElementMessage()}" +
                            "  ---  $msg").toByteArray(charset)
                    fileOutputStream.write(dataBytes)
                    fileOutputStream.write(t.toByteArray(charset))
                    fileOutputStream.close()
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun targetStackTraceElementMessage(): String{
        val stackTraceElement = targetStackTraceElement()
        return "(${stackTraceElement?.fileName}:${stackTraceElement?.lineNumber}) ------ "
    }

    private fun targetStackTraceElement(): StackTraceElement? {
        var targetStackTrace: StackTraceElement? = null
        var shouldTrace = false
        val stackTrace = Thread.currentThread().stackTrace
        for (stackTraceElement in stackTrace){
            val isLogMethod = stackTraceElement.className == LogUtil::class.java.name;
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement
                break
            }
            shouldTrace = isLogMethod
        }
        return targetStackTrace
    }

    private fun nowTime(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        val nowTine = formatter.format(Date())
        return "[$nowTine]"
    }

    private fun nowTimeShort(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        return formatter.format(Date())
    }
}