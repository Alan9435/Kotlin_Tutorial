package com.example.tutorial_example.Fragment

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import javax.crypto.Cipher

@RunWith(JUnit4::class)
class RsaFragmentTest {

    @Test
    fun verifyCode(){
        val resultText = "test1234"
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        val keyPair = keyPairGenerator.generateKeyPair()
        val publicKey = keyPair.public as RSAPublicKey
        val privateKey = keyPair.private as RSAPrivateKey

        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encrypted = cipher.doFinal(resultText.toByteArray(Charsets.UTF_8))

        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val decrypted = cipher.doFinal(encrypted)
        val decryptedText = String(decrypted, Charsets.UTF_8)

        assertEquals(resultText,decryptedText) //使用斷言判斷加密前後字串相等
    }
}