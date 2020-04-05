package com.zk.common.encryption

import com.zk.common.utils.DataConversion.convertHexString
import com.zk.common.utils.DataConversion.toHexString
import org.apache.commons.codec.binary.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec

/**
 * 若要使用该方法请引用
 * implementation'apache-codec:commons-codec:1.2'
 */
object DESUtil {
    //密钥
    const val DEFAULT_KEY = "ZhuKun"

    /**
     * 加密
     * @param message
     * @param key
     * @return
     */
    fun encrypt(message: String, key: String): ByteArray {
        val cipher: Cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")

        val desKeySpec = DESKeySpec(key.toByteArray(charset("UTF-8")))

        val keyFactory: SecretKeyFactory = SecretKeyFactory.getInstance("DES")
        val secretKey: SecretKey = keyFactory.generateSecret(desKeySpec)
        val iv = IvParameterSpec(key.toByteArray(charset("UTF-8")))
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)

        val charset = Charsets.UTF_8
        return cipher.doFinal(message.toByteArray(charset))
    }

    /**
     * 加密，方式为先DES加密， 再base64转码的字符串
     * @param message
     * @param key
     * @return
     */
    fun encrypt_base64(message: String, key: String): String {
        val a: String = toHexString(encrypt(message, key)).toUpperCase()
        return String(Base64.encodeBase64(a.toByteArray(charset("UTF-8"))))
    }

    /**
     * 解密
     * @param message    加密后的内容
     * @param key        密钥
     * @return
     */
    fun decrypt(message: String, key: String): String {
        val byteSrc: ByteArray = convertHexString(message)
        val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")
        val desKeySpec = DESKeySpec(key.toByteArray(charset("UTF-8")))
        val keyFactory = SecretKeyFactory.getInstance("DES")
        val secretKey = keyFactory.generateSecret(desKeySpec)
        val iv = IvParameterSpec(key.toByteArray(charset("UTF-8")))

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)

        val retByte = cipher.doFinal(byteSrc)
        val charset = Charsets.UTF_8
        return retByte.toString(charset)
    }


    /**
     * 解密，方式为先解码base64，再解密，转成正常字符串
     * @param base64message   base64编码的密文
     * @param key             密钥
     * @return                解密后的文本
     */
    @Throws(Exception::class)
    fun decrypt_base64(base64message: String, key: String): String {
        val message = String(Base64.decodeBase64(base64message.toByteArray(charset("UTF-8"))))
        return decrypt(message, key)
    }
}