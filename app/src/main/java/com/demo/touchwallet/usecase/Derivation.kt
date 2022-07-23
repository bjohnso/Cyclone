package com.demo.touchwallet.usecase

import com.demo.touchwallet.extensions.StringExtensions.decodeBinaryString
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import kotlin.math.pow

object Derivation {
    operator fun invoke(seed: ByteArray): List<AsymmetricCipherKeyPair> {
        // path = m/44'/501'

        val keyPairs = mutableListOf<AsymmetricCipherKeyPair>()

        val indices = MutableList(66) {
            when (it) {
                0 -> 44
                1 -> 501
                else -> it - 2
            }
        }

        var hmac = BitSet.valueOf(
            hashSeed(
                seed = seed,
                salt = "mnemonic".toByteArray()
            )
        )

        var privateKey = MnemonicEncoder.getBinaryString(hmac, 0, 255) ?: ""
        var chainCode = MnemonicEncoder.getBinaryString(hmac, 256, 511) ?: ""

        indices.forEachIndexed { i, item ->
            val index = (2.toDouble().pow(31) + item)
                .toBigDecimal()
                .toBigIntegerExact()
                .toString(2)

            val message = (privateKey + index)
                .padStart(296, '0')

            hmac = BitSet.valueOf(
                hashSeed(
                    seed = message.decodeBinaryString(),
                    salt = chainCode.decodeBinaryString()
                )
            )

            val entropy = MnemonicEncoder.getBinaryString(hmac, 0, 255) ?: ""

            privateKey = deriveChildPrivateKey(
                childEntropy = entropy,
                parentKey = chainCode
            )

            chainCode = MnemonicEncoder.getBinaryString(hmac, 256, 511) ?: ""

            if (i > 1) {
                val keyPair = getEd25519KeyPairFromPrivateKey(
                    privateKey.decodeBinaryString()
                )

                keyPairs.add(keyPair)
            }
        }

        return keyPairs
    }

    fun getEd25519KeyPairFromPrivateKey(byteArray: ByteArray): AsymmetricCipherKeyPair {
        val privateKey = Ed25519PrivateKeyParameters(byteArray)
        val publicKey = privateKey.generatePublicKey()

        return AsymmetricCipherKeyPair(
            publicKey, privateKey
        )
    }

    fun hashSeed(seed: ByteArray, salt: ByteArray? = null): ByteArray {
        val messageDigest = MessageDigest.getInstance("SHA-512")
        if (salt != null) {
            messageDigest.update(salt)
        }

        return messageDigest.digest(seed)
    }

    fun deriveChildPrivateKey(childEntropy: String, parentKey: String): String {
        val ed25519Order = BigInteger(
            "10000000D73E37DB0F1F98CCBE59434D5FAA8B4D70C9B0800000000000000000",
            16
        )

        val key = BigInteger(childEntropy, 2) + BigInteger(parentKey, 2)
        val result = key % ed25519Order
        return result.toString(2).padStart(256, '0')
    }
}