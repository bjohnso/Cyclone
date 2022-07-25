package com.demo.touchwallet.crypto

import android.content.Context
import com.demo.touchwallet.R
import com.demo.touchwallet.extensions.ByteExtensions.toBinaryString
import com.demo.touchwallet.extensions.StringExtensions.decodeBinaryString
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

object MnemonicEncoder {
    operator fun invoke(context: Context, seed: ByteArray): List<String>? {
        val mnemonicWordList = context.resources.getStringArray(R.array.mnemonic_word_list)

        val checksumLength = seed.size / 4
        val entropyLength = seed.size * 8

        val entropyBinaryString = BitSet
            .valueOf(seed)
            .toBinaryString(0)
            ?.padStart(entropyLength, '0') ?: ""

        val hashedSeed = hashEntropy(
            entropyBinaryString.decodeBinaryString()
        )

        val hashedSeedBits = BitSet.valueOf(hashedSeed)

        val mnemonicBinaryString = "$entropyBinaryString${
            hashedSeedBits.toBinaryString(
                hashedSeedBits.length() - checksumLength
            )
        }"

        if (mnemonicBinaryString.length != entropyLength + checksumLength) {
            return null
        }

        return mnemonicBinaryString
            .chunked(11)
            .map {
                val index = BigInteger(it, 2).toInt()
                mnemonicWordList[index]
            }
    }

    private fun hashEntropy(entropy: ByteArray): ByteArray {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        return messageDigest.digest(entropy)
    }
}