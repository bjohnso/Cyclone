package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.R
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

object MnemonicGenerator {
    operator fun invoke(context: Context, seed: ByteArray): List<String> {
        val mnemonicWordList = context.resources.getStringArray(R.array.mnemonic_word_list)

        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashedSeed = messageDigest.digest(seed)

        val checksumLength = seed.size / 32
        val checksum = hashedSeed.take(checksumLength)

        val mnemonicBytes = seed + checksum
        val mnemonicBits = BitSet.valueOf(mnemonicBytes)
        var mnemonicBinaryString = ""

        for (i in 0 until mnemonicBits.length()) {
            mnemonicBinaryString += when (mnemonicBits[i]) {
                true -> 1
                else -> 0
            }
        }

        return mnemonicBinaryString
            .chunked(11)
            .map {
                val index = BigInteger(it, 2).toInt()
                mnemonicWordList[index]
            }
    }
}