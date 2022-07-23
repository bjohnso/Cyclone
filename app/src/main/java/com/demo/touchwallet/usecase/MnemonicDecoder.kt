package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.R
import com.demo.touchwallet.extensions.ByteExtensions.toBinaryString
import com.demo.touchwallet.extensions.ExceptionExtensions
import java.security.MessageDigest
import java.util.*

object MnemonicDecoder {
    operator fun invoke(context: Context, mnemonicList: List<String>): ByteArray? {
        val mnemonicWordList = context.resources.getStringArray(R.array.mnemonic_word_list)

        val mnemonicBitLength = mnemonicList.size * 11
        val entropyLength = (mnemonicBitLength * 32) / 33

        var mnemonicBinaryString = ""

        ExceptionExtensions.tryOrDefault(null) {
            mnemonicList
                .forEach {
                    val decimal = mnemonicWordList.indexOf(it)
                    require(decimal > -1)

                    val binaryString = Integer
                        .toBinaryString(1 shl 11 or decimal)
                        .substring(1)

                    mnemonicBinaryString += binaryString
                }
        }

        if (mnemonicBinaryString.length != mnemonicBitLength) {
            return null
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

    private fun validateChecksumForEntropy(entropy: ByteArray, checksum: String): Boolean {
        val checksumLength = entropy.size / 4
        val hashedEntropy = hashEntropy(entropy)

        val hashedEntropyBits = BitSet.valueOf(hashedEntropy)
        val hashedChecksum = hashedEntropyBits.toBinaryString(
            hashedEntropyBits.length() - checksumLength,
            hashedEntropyBits.length() - 1
        )

        return checksum == hashedChecksum
    }

    private fun hashEntropy(entropy: ByteArray): ByteArray {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        return messageDigest.digest(entropy)
    }
}