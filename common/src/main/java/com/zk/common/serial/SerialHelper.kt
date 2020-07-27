package com.zk.common.serial

import com.zk.common.serial.android_serialport_api.SerialPort
import com.zk.common.utils.LogUtil
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.security.InvalidParameterException
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Created by ZK on 2018/1/2.
 * 串口辅助工具类
 */
abstract class SerialHelper(port: String, baudRate: Int) {
    private lateinit var mSerialPort: SerialPort
    private lateinit var mOutputStream: OutputStream
    private lateinit var mInputStream: InputStream
    private lateinit var mReadThread: ReadThread
    private var mSendThread: SendThread? = null

    private var mSendFlag = true

    // 待发送数据队列
    private val mSendList = LinkedList<ByteArray>() //TODO LinkedList理解的不是很深入
    // 写线程锁
    private val mSendLock = ReentrantLock()
    private val mSendCondition = mSendLock.newCondition()

    private var isOpen = false
    val mPort = port
    val mBaudRate = baudRate


    @Throws(SecurityException::class, IOException::class, InvalidParameterException::class)
    open fun open(withSend: Boolean) {
        if (!isOpen) {
            isOpen = true
            mSendFlag = true

            mSerialPort = SerialPort(File(mPort), mBaudRate, 0)
            mOutputStream = mSerialPort.outputStream
            mInputStream = mSerialPort.inputStream

            mReadThread = ReadThread()
            mReadThread.start()

            if (withSend) {
                mSendThread = SendThread()
                mSendThread!!.start()
            }
        }
    }

    open fun close() {
        mReadThread.interrupt()
        mSerialPort.close()
        isOpen = false
        if (mSendThread != null) mSendThread!!.interrupt()
        mSendFlag = false
        mSendLock.withLock {
            mSendCondition.signal()
        }
    }

    open fun addSendTask(sendDate: ByteArray): Boolean {
        var ret = false
        if (mSendThread != null) {
            mSendLock.withLock {
                ret = mSendList.offer(sendDate)
                mSendCondition.signal()
            }
        }
        return ret
    }

    /**
     * 读取线程
     */
    private inner class ReadThread : Thread() {
        override fun run() {
            super.run()
            while (!isInterrupted) {
                try {
                    val buffer = ByteArray(2048)
                    val size: Int = mInputStream.read(buffer)
                    if (size > 0) {
                        val readBuffer = ByteArray(size)
                        System.arraycopy(buffer, 0, readBuffer, 0, size)
                        onDataReceived(mPort, readBuffer, size)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    return
                }
            }
        }
    }

    private inner class SendThread : Thread() {
        override fun run() {
            super.run()
            while (!isInterrupted) {
                while (mSendFlag) {
                    mSendLock.withLock {
                        if (mSendList.size == 0) {
                            LogUtil.instance.d("SerialHelper", "mSendCondition.await", false)
                            mSendCondition.await(1, TimeUnit.SECONDS)
                        }
                    }
                    if (mSendList.size > 0) {
                        var dataOperating: ByteArray? = null
                        try {
                            dataOperating = mSendList.poll()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        if (dataOperating != null) {
                            // 写数据
                            send(dataOperating)
                        }
                        //非常重要，每发完一针一定要sleep
                        try {
                            sleep(200)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    private fun send(outArray: ByteArray) {
        LogUtil.instance.d("串口通信Android发送：", outArray, outArray.size)
        try {
            mOutputStream.write(outArray)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 是否打开读写线程
     */
    fun isOpen(): Boolean {
        return isOpen
    }

    /**
     * 收到的数据回调
     *
     * @param sPort  串口端口
     * @param buffer 数据
     * @param size   长度
     */
    protected abstract fun onDataReceived(sPort: String?, buffer: ByteArray, size: Int)
}