package com.zk.common.utils

import android.graphics.Bitmap
import android.os.Environment
import java.io.*
import java.nio.channels.FileChannel

class FileUtils {
    /**
     * 检查SD卡是否可用
     */
    fun isSdCardAvailable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * 获取SD卡根目录
     */
    fun getSDPathStr(): String? {
        return if (isSdCardAvailable()) {
            Environment.getExternalStorageDirectory().path
        } else {
            null
        }
    }

    /**
     * 判断文件是否存在
     */
    fun isFileExist(fileDirectory: String, fileName: String?): File? {
        val filePath = when(fileName == null){
            true -> fileDirectory
            false -> "$fileDirectory/$fileName"
        }
        val file: File?
        try {
            file = File(filePath)
            if (!file.exists()) {
                return null
            }
        } catch (e: Exception) {
            return null
        }
        return file
    }

    /**
     * 删除文件
     */
    fun deleteFile(fileDirectory: String, fileName: String?) {
        val filePath = when(fileName == null){
            true -> fileDirectory
            false -> "$fileDirectory/$fileName"
        }
        try {
            File(filePath).delete()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 读取txt文件的内容
     */
    fun txtToString(fileDirectory: String, fileName: String?): String? {
        val filePath = when(fileName == null){
            true -> fileDirectory
            false -> "$fileDirectory/$fileName"
        }
        val file = File(filePath)
        if (!file.exists()) {
            return null
        }
        val result = StringBuilder()
        try {
            //构造一个BufferedReader类来读取文件
            val br = BufferedReader(FileReader(file))
            var s: String?
            //使用readLine方法，一次读一行
            while (br.readLine().also { s = it } != null) {
                result.append(System.lineSeparator() + s)
            }
            br.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return result.toString()
    }

    /**
     * 写入txt文件
     */
    fun writeTxtFile(fileDirectory: String, fileName: String?, msg: String): Boolean {
        val filePath = when(fileName == null){
            true -> fileDirectory
            false -> "$fileDirectory/$fileName"
        }
        val file = File(filePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        var flag = false
        val fileOutputStream: FileOutputStream?
        try {
            fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(msg.toByteArray(charset("utf-8")))
            fileOutputStream.close()
            flag = true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return flag
    }

    /**
     * 保存图片
     * todo 待更新
     */
    fun saveBitmap(file: File?, bitmap: Bitmap): Boolean {
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            return true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        return false
    }

    /**
     * 根据文件路径拷贝文件
     * 使用FileChannel
     * Java NIO类库里约会了一个叫transferFrom的方法，文档里说这是一个会比FileStream方式重启的复制操作。
     */
    fun copyFile(src: File, destPath: String, fileName: String?): Boolean {
        val result: Boolean
        val dest = File(destPath + File.separator + fileName)
        if (dest.exists() && !dest.delete()) {
            return false
        }
        try {
            dest.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        val srcChannel: FileChannel
        val dstChannel: FileChannel
        try {
            srcChannel = FileInputStream(src).channel
            dstChannel = FileOutputStream(dest).channel
            srcChannel.transferTo(0, srcChannel.size(), dstChannel)
            result = true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        try {
            srcChannel.close()
            dstChannel.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }


}