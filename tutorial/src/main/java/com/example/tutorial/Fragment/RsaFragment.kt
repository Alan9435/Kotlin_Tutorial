package com.example.tutorial.Fragment

import android.util.Log
import com.example.common.Base.BaseFragment
import com.example.tutorial.databinding.FragmentRsaBinding
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import javax.crypto.Cipher
import kotlin.math.log

class RsaFragment: BaseFragment<FragmentRsaBinding>() {

    override fun onViewInit() {
        /**
         * KeyPairGenerator是Java加密API提供的一個類別，用於生成密鑰對(KeyPair)*/
        //創建了一個KeyPairGenerator物件 指定使用RSA演算法
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        //RSA公鑰和私鑰的位數 長度越長安全性越高 但加解密計算時間成本越高
        keyPairGenerator.initialize(2048)
        val keyPair = keyPairGenerator.generateKeyPair()
        val publicKey = keyPair.public as RSAPublicKey  //產生公鑰
        val privateKey = keyPair.private as RSAPrivateKey   //產生私鑰

        /**
         * Cipher物件是Java加密API提供的一個類別，用於執行加密或解密操作。
         * 在這裡，我們使用Cipher.getInstance("RSA/ECB/PKCS1Padding")方法獲取一個執行RSA算法的Cipher物件，其中：
         * "RSA"表示使用RSA算法；
         * "ECB"表示使用電子密碼本模式（Electronic Codebook mode）；
         * "PKCS1Padding"表示使用RSA加密填充模式之一，用於確保加密的數據能夠適當地進行填充，以防止攻擊者從加密的數據中推斷出任何有用的信息。*/
        //指定要使用RSA/ECB/PKCS1Padding加密解密方式
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)     //初始化加密
        val encrypted = cipher.doFinal("test123".toByteArray(Charsets.UTF_8)) //使用RSA公鑰加密後得到結果
        Log.d("***********", "加密後: ${encrypted}")

        cipher.init(Cipher.DECRYPT_MODE, privateKey)  //初始化解密
        val decrypted = cipher.doFinal(encrypted)     //使用私鑰解密
        val decryptedText = String(decrypted, Charsets.UTF_8) //獲得解密

        //使用單元測試來驗證加解密 藍芽 網路影片播放

        Log.d("***********", "dec: ${decryptedText}")
    }
}