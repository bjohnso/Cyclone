package com.demo.touchwallet.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.touchwallet.ui.state.SeedPhraseRecoveryUiState
import com.demo.touchwallet.usecase.MnemonicDecoder

class SeedPhraseRecoveryViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = SeedPhraseRecoveryUiState(),
        policy = neverEqualPolicy()
    )
        private set

    fun decodeMnemonic(context: Context) {
        val mnemonicList = when {
            isValid12WordMnemonic() -> uiState.seedWords.take(12).map { it.trim() }
            isValid24WordMnemonic() -> uiState.seedWords.take(24).map { it.trim() }
            else -> null
        }

        if (!mnemonicList.isNullOrEmpty()) {
            MnemonicDecoder.invoke(
                context = context,
                mnemonicList = mnemonicList
            )
        }
    }

    fun isValid12WordMnemonic(): Boolean {
        val isNot24WordSeedPhrase = uiState.seedWords
            .takeLast(12)
            .all { it.isBlank() }

        val is12WordSeedPhrase = uiState.seedWords
            .take(12)
            .all { it.isNotBlank() }

        return isNot24WordSeedPhrase && is12WordSeedPhrase
    }

    fun isValid24WordMnemonic(): Boolean {
        return  uiState.seedWords
            .take(24)
            .all { it.isNotBlank() }
    }

    fun updateWordAtCurrentIndex(word: String?) {
        word?.let {
            uiState.seedWords[uiState.currentIndex] = it
            uiState = uiState
        }
    }

    fun funIncrementIndex() {
        uiState.currentIndex = (++uiState.currentIndex)
            .coerceAtMost(uiState.seedWords.size -1)
        uiState.currentWord = uiState.seedWords[uiState.currentIndex]
        uiState = uiState
    }

    fun funDecrementIndex() {
        uiState.currentIndex =
            (--uiState.currentIndex).coerceAtLeast(0)
        uiState.currentWord = uiState.seedWords[uiState.currentIndex]
        uiState = uiState
    }
}

