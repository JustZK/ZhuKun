package com.zk.common.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class PingUtil {
    private val tag = "PingUtil"

    class PingEntity(context: Context, ip: String, pingCount: Int, pingWaitTime: Int) {
        // init
        val context = context
        val ip = ip //Ping的地址
        val pingCount = pingCount //Ping的次数
        val pingWaitTime = pingWaitTime //等待时间/设置超时的时间
        //return
        var resultBuffer: StringBuffer? = null //ping得到的数据
        var pingTime: String? = null //ping通的时间
        var result = false //结果
    }

    /**
     * ping
     * @param pingEntity 检测网络实体类
     * @return 检测后的数据
     * 请在子线程中调用
     * Thread(Runnable {
     *     var pingEntity = PingUtil.PingEntity(this,"www.baidu.com", 5, 10)
     *     pingEntity = PingUtil().ping(pingEntity)
     * }).start()
     */
    fun ping(pingEntity: PingEntity): PingEntity {
        if (pingEntity.resultBuffer == null) pingEntity.resultBuffer = StringBuffer()
        var line: String?
        var process: Process? = null
        var successReader: BufferedReader? = null
        val command = "ping -c " + pingEntity.pingCount + " -w " + pingEntity.pingWaitTime + " " + pingEntity.ip
        //String command = "ping -c " + pingCount + " " + host;
        try {
            process = Runtime.getRuntime().exec(command)
            if (process == null) {
                LogUtil.instance.d(tag, "ping失败:process空值，获取实例化失败.")
                pingEntity.resultBuffer?.append("ping失败:process空值，获取实例化失败.\n")
                pingEntity.pingTime = null
                pingEntity.result = false
                return pingEntity
            }
            successReader = BufferedReader(InputStreamReader(process.inputStream))
            do {
                line = successReader.readLine()
                if (line == null) break
                LogUtil.instance.d(tag, line)
                pingEntity.resultBuffer?.append("$line\n")
                val time = getTime(line)
                if (time != null) pingEntity.pingTime = time
            } while (true)
            val status = process.waitFor()
            if (status == 0) {
                LogUtil.instance.d(tag, "exec cmd success:$command")
                pingEntity.resultBuffer?.append("exec cmd success (成功):$command\n")
                pingEntity.result = true
            } else {
                LogUtil.instance.d(tag, "exec cmd fail.")
                pingEntity.resultBuffer?.append("exec cmd fail (失败).\n")
                pingEntity.pingTime = null
                pingEntity.result = false
            }
            LogUtil.instance.d(tag, "exec finished.")
            pingEntity.resultBuffer?.append("exec finished.")
        } catch (e: IOException) {
            LogUtil.instance.d(tag, e.toString())
            pingEntity.resultBuffer?.append("IOException:$e")
        } catch (e: InterruptedException) {
            LogUtil.instance.d(tag, e.toString())
            pingEntity.resultBuffer?.append("InterruptedException:$e")
        } finally {
            LogUtil.instance.d(tag, "ping exit.")
            process?.destroy()
            if (successReader != null) {
                try {
                    successReader.close()
                } catch (e: IOException) {
                    LogUtil.instance.d(tag, e.toString())
                }

            }
        }
        LogUtil.instance.d(tag, pingEntity.resultBuffer.toString())
        return pingEntity
    }

    private fun getTime(line: String): String? {
        val lines = line.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var time: String? = null
        for (l in lines) {
            if (!l.contains("time="))
                continue
            val index = l.indexOf("time=")
            time = l.substring(index + "time=".length)
            LogUtil.instance.d(tag, time)
        }
        return time
    }
}