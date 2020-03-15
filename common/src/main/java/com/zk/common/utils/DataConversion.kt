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
}