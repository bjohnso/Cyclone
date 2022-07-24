package com.demo.touchwallet.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.touchwallet.R
import com.demo.touchwallet.entity.KeyPairEntity
import com.demo.touchwallet.ui.models.TokenModel
import com.demo.touchwallet.ui.state.WalletUiState
import com.demo.touchwallet.usecase.CreateWalletUseCase
import com.demo.touchwallet.usecase.RetrieveWalletUseCase
import kotlinx.coroutines.flow.*

class WalletViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = WalletUiState(),
        policy = neverEqualPolicy()
    )
        private set

    fun flowOnWallet(context: Context): Flow<KeyPairEntity?> {
        return flow {
            val keyPair = RetrieveWalletUseCase.retrieveCurrentWallet(
                context = context
            ) ?: CreateWalletUseCase.createKeypair(
                context = context
            )

            emit(keyPair)
        }.distinctUntilChanged()
            .onEach { onWallet(it) }
    }

    fun flowOnTokenList(): Flow<List<TokenModel>> {
        return flow {
            emit(
                listOf(
                    TokenModel(
                        "Solana",
                        0f,
                        R.drawable.ic_sol
                    )
                )
            )
        }.filterNotNull()
            .distinctUntilChanged()
            .onEach { onTokenList(it) }
    }

    private fun onWallet(keyPairEntity: KeyPairEntity?) {
        keyPairEntity?.let {
            uiState = uiState.copy(currentAddress = keyPairEntity.publicKey)
        }
    }

    private fun onTokenList(tokens: List<TokenModel>) {
        uiState = uiState.copy(
            isLoading = false,
            hasError = false,
            currentTotalBalance = tokens
                .map { t -> t.tokenBalance }
                .reduceOrNull { acc, next -> acc + next } ?: 0f,
            tokens = tokens
        )
    }
}