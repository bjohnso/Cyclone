package com.demo.touchwallet.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.touchwallet.repository.SolanaRepository
import com.demo.touchwallet.ui.composable.wallet.SeedPhraseGridState
import kotlinx.coroutines.flow.*

class WalletSeedCreationViewModel: ViewModel() {
    var uiState by mutableStateOf(SeedPhraseGridState())
        private set

    fun flowOnCreateSeed(context: Context): Flow<Boolean> {
        return flow {
            emit(
                SolanaRepository
                .getInstance(context = context)
                .generateKeyPair()
            )
        }.filterNotNull().distinctUntilChanged()
    }

    fun flowStateOnSeedWords(context: Context): Flow<SeedPhraseGridState?> {
        return SolanaRepository
            .getInstance(context = context)
            .flowOnSeed()
            .filterNotNull()
            .distinctUntilChanged()
            .map {
                uiState.seedWords = it.getMnemonic(context = context)
                uiState = uiState.apply {
                    isLoading = false
                    seedWords = it.getMnemonic(context = context)
                    error = null
                }
                Log.e("TEST_SEED", "FLOW ON SEEDS - ${uiState.seedWords?.joinToString(",")}")

                uiState
            }
    }
}