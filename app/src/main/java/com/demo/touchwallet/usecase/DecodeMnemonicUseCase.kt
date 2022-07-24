package com.demo.touchwallet.usecase

import android.content.Context

object DecodeMnemonicUseCase {
    suspend fun decodeMnemonic(seedWords: List<String>, context: Context): Boolean {
        val mnemonicList = when {
            isValid12WordMnemonic(seedWords = seedWords) -> {
                seedWords.take(12).map { it.trim() }
            }
            isValid24WordMnemonic(seedWords = seedWords) -> {
                seedWords.take(24).map { it.trim() }
            }
            else -> null
        }

        return if (!mnemonicList.isNullOrEmpty()) {
            CreateWalletUseCase.restoreSeed(
                context = context,
                mnemonics = mnemonicList
            )
        } else false
    }

    fun isValid12WordMnemonic(seedWords: List<String>): Boolean {
        val isNot24WordSeedPhrase = seedWords
            .takeLast(12)
            .all { it.isBlank() }

        val is12WordSeedPhrase = seedWords
            .take(12)
            .all { it.isNotBlank() }

        return isNot24WordSeedPhrase && is12WordSeedPhrase
    }

    fun isValid24WordMnemonic(seedWords: List<String>): Boolean {
        return seedWords
            .take(24)
            .all { it.isNotBlank() }
    }
}