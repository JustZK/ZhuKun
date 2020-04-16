package com.zk.common.utils

object DataConversion {

    /**
     * byte数组转成16进制
     * 不足两位的补0
     */
    fun toHexString(b: ByteArray): String {
        val hexString = StringBuffer()
        for (i in b.indices) {
            var plainText = Integer.toHexString(0xff and b[i].toInt())
            if (plainText.length < 2) plainText = "0$plainText"
            hexString.append(plainText)
        }
        return hexString.toString()
    }

    /**
     * 十六进制字符串转换byte数组（和上面那个对应）
     */
    fun convertHexString(ss: String): ByteArray {
        val digest = ByteArray(ss.length / 2)
        for (i in digest.indices) {
            val byteString = ss.substring(2 * i, 2 * i + 2)
            val byteValue = byteString.toInt(16)
            digest[i] = byteValue.toByte()
        }
        return digest
    }

    /**
     * 合并两个byte数组
     */
    fun bytesMerger(byteHead: ByteArray, byteTail: ByteArray): ByteArray {
        val byte3 = ByteArray(byteHead.size + byteTail.size)
        System.arraycopy(byteHead, 0, byte3, 0, byteHead.size)
        System.arraycopy(byteTail, 0, byte3, byteHead.size, byteTail.size)
        return byte3
    }

    /**
     * 和校验
     *
     * @param buffer 校验buffer
     * @param size   长度
     */
    fun sumCheckLong(buffer: ByteArray, size: Int): Int {
        var sum = 0
        for (i in 0 until size) {
            val num = if (buffer[i].toInt() >= 0) buffer[i].toInt() else buffer[i].toInt() + 256
            sum += num
        }
        return sum
    }

    //两个byte转成int
    fun twoByte2Int(b: ByteArray, off: Int): Int {
        var n = 0
        n = n or (0xFF and b[off].toInt()) shl 8
        n = n or (0xFF and b[off + 1].toInt())
        return n
    }

    //两个byte转成int
    fun twoByte2Int(b: ByteArray): Int {
        var n = 0
        n = n or (0xFF and b[0].toInt()) shl 8
        n = n or (0xFF and b[1].toInt())
        return n
    }
}