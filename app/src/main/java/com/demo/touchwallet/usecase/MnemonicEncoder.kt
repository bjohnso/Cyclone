package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.R
import com.demo.touchwallet.extensions.ByteExtensions.toBinaryString
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

        mnemonicBinaryString += entropyBits.toBinaryString(
            0,
            entropyBits.length() - 1
        )

        mnemonicBinaryString += hashedSeedBits.toBinaryString(
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

    private fun hashEntropy(entropy: ByteArray): ByteArray {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        return messageDigest.digest(entropy)
    }
}