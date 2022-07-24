package com.demo.touchwallet.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.touchwallet.repository.SolanaRepository
import com.demo.touchwallet.ui.state.SeedPhraseCreationUiState
import kotlinx.coroutines.flow.*

class SeedCreationViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = SeedPhraseCreationUiState(),
        policy = neverEqualPolicy()
    )
        private set

    fun flowStateOnSeedWords(context: Context): Flow<SeedPhraseCreationUiState?> {
        return SolanaRepository
            .getInstance(context = context)
            .flowOnSeed()
            .filterNotNull()
            .distinctUntilChanged()
            .map {
                uiState = uiState.copy(
                    isLoading = false,
                    hasError = false,
                    seedWords = it.getMnemonic(context = context)
                )
                uiState
            }
    }
}