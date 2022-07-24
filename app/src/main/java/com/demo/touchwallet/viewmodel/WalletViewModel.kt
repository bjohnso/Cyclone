package com.demo.touchwallet.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.touchwallet.R
import com.demo.touchwallet.repository.SolanaRepository
import com.demo.touchwallet.ui.models.AccountModel
import com.demo.touchwallet.ui.models.TokenModel
import com.demo.touchwallet.ui.state.WalletUiState
import com.demo.touchwallet.usecase.Base58Encoder
import kotlinx.coroutines.flow.*
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters

class WalletViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = WalletUiState(),
        policy = neverEqualPolicy()
    )
        private set

    fun flowOnWallet() {

    }

    fun flowOnTokenList(
        walletAddress: String,
        context: Context
    ): Flow<List<TokenModel>> {
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
            .map {
                uiState = uiState.copy(
                    isLoading = false,
                    hasError = false,
                    currentAddress = walletAddress,
                    currentTotalBalance = it
                        .map { t -> t.tokenBalance }
                        .reduceOrNull { acc, next -> acc + next } ?: 0f,
                    tokens = it
                )

                it
            }
    }
}