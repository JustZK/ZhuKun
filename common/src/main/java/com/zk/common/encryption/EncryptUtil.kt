package com.zk.common.encryption

import android.util.Base64
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec

object EncryptUtil {

    /**
     * 解密
     */
    fun desEncrypt(data: ByteArray, desKey: ByteArray, desVI: ByteArray): ByteArray? {
        var result: ByteArray? = null
        try {
            // 密钥
            val desKeySpec = DESKeySpec(desKey)
            // 向量
            val ivSpec = IvParameterSpec(desVI)
            val paramSpec: AlgorithmParameterSpec = ivSpec

            val keyFactory = SecretKeyFactory.getInstance("DES")
            val secureKey = keyFactory.generateSecret(desKeySpec)
            // DES加密模式CBC
            val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")

            cipher.init(Cipher.ENCRYPT_MODE, secureKey, paramSpec)
            result = Base64.encode(cipher.doFinal(data), Base64.DEFAULT)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun desDecrypt(data: ByteArray, desKey: ByteArray, desVI: ByteArray): ByteArray? {
        var result: ByteArray? = null
        try {
            // 密钥
            val desKeySpec = DESKeySpec(desKey)
            // 向量
            val ivSpec = IvParameterSpec(desVI)
            val paramSpec: AlgorithmParameterSpec = ivSpec

            val keyFactory = SecretKeyFactory.getInstance("DES")
            val secureKey = keyFactory.generateSecret(desKeySpec)
            // DES加密模式CBC
            val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")

            cipher.init(Cipher.DECRYPT_MODE, secureKey, paramSpec)

            result = cipher.doFinal(Base64.decode(data, Base64.DEFAULT))
            //result = Base64.encode(cipher.doFinal(data), Base64.DEFAULT);
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return result
    }
}