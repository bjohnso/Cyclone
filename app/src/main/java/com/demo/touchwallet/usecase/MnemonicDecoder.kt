package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.R
import java.util.*

object MnemonicDecoder {
    operator fun invoke(context: Context, mnemonicList: List<String>): ByteArray? {
        val mnemonicWordList = context.resources.getStringArray(R.array.mnemonic_word_list)

        val mnemonicBitLength = mnemonicList.size * 11
        val entropyLength = (mnemonicBitLength * 32) / 33

        var mnemonicBinaryString = ""

        mnemonicList
            .forEach {
                val decimal = mnemonicWordList.indexOf(it)
                require(decimal > -1)

                val binaryString = Integer
                    .toBinaryString(1 shl 11 or decimal)
                    .substring(1)

                mnemonicBinaryString += binaryString
            }

        val checksumBinaryString = mnemonicBinaryString.takeLast(
            mnemonicBitLength - entropyLength
        )

        mnemonicBinaryString = mnemonicBinaryString.take(entropyLength)

        val entropy = BitSet(mnemonicBinaryString.length).apply {
            for (i in mnemonicBinaryString.indices) {
                this[i] = when(mnemonicBinaryString[i]) {
                    '1' -> true
                    else -> false
                }
            }
        }.toByteArray()

        return if (
            validateChecksumForEntropy(
                entropy = entropy,
                checksum = checksumBinaryString
            )
        ) entropy else null
    }

    fun validateChecksumForEntropy(entropy: ByteArray, checksum: String): Boolean {
        val checksumLength = entropy.size / 4
        val hashedEntropy = MnemonicEncoder.hashEntropy(entropy)

        val hashedEntropyBits = BitSet.valueOf(hashedEntropy)
        val hashedChecksum = MnemonicEncoder.getBinaryString(
            hashedEntropyBits,
            hashedEntropyBits.length() - checksumLength,
            hashedEntropyBits.length() - 1
        )

        return checksum == hashedChecksum
    }
}