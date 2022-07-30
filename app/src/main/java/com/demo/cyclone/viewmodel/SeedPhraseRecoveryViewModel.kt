package com.demo.cyclone.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.cyclone.ui.state.SeedPhraseRecoveryUiState

class SeedPhraseRecoveryViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = SeedPhraseRecoveryUiState(),
        policy = neverEqualPolicy()
    )
        private set

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

