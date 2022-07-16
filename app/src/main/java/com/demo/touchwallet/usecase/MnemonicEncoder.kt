package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.R
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

object MnemonicEncoder {
    operator fun invoke(context: Context, seed: ByteArray): List<String> {
        val mnemonicWordList = context.resources.getStringArray(R.array.mnemonic_word_list)

        val checksumLength = seed.size / 4
        val hashedSeed = hashEntropy(seed)

        val hashedSeedBits = BitSet.valueOf(hashedSeed)
        val entropyBits = BitSet.valueOf(seed)

        var mnemonicBinaryString = ""

        mnemonicBinaryString += getBinaryString(
            entropyBits,
            0,
            entropyBits.length() - 1
        )

        mnemonicBinaryString += getBinaryString(
            hashedSeedBits,
            hashedSeedBits.length() - checksumLength,
            hashedSeedBits.length() - 1
        )

        return mnemonicBinaryString
            .chunked(11)
            .map {
                val index = BigInteger(it, 2).toInt()
                mnemonicWordList[index]
            }
    }

    fun hashEntropy(entropy: ByteArray): ByteArray {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        return messageDigest.digest(entropy)
    }

    fun getBinaryString(bitSet: BitSet, startIndex: Int, endIndex: Int): String? {
        var binaryString = ""

        for (i in startIndex..endIndex) {
            binaryString += when(bitSet[i]) {
                true -> '1'
                else -> '0'
            }
        }

        return binaryString.ifBlank { null }
    }
}