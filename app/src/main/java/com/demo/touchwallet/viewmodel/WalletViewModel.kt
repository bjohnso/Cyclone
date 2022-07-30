package com.demo.touchwallet.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.touchwallet.R
import com.demo.touchwallet.ui.models.SolanaAccountModel
import com.demo.touchwallet.ui.models.TokenModel
import com.demo.touchwallet.ui.state.WalletUiState
import com.demo.touchwallet.usecase.CreateWalletUseCase
import com.demo.touchwallet.usecase.GetAccountBalanceUseCase
import com.demo.touchwallet.usecase.RetrieveWalletUseCase
import kotlinx.coroutines.flow.*

class WalletViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = WalletUiState(),
        policy = neverEqualPolicy()
    )
        private set

    fun flowOnWallet(context: Context): Flow<SolanaAccountModel?> {
        return flow {
            val keyPair = RetrieveWalletUseCase.retrieveCurrentWallet(
                context = context
            ) ?: CreateWalletUseCase.createKeypair(
                context = context
            )

            emit(
                if (keyPair?.publicKey != null) {
                    GetAccountBalanceUseCase.getAccountBalance(
                        context = context,
                        pubKey = keyPair.publicKey,
                    )
                } else null
            )
        }.distinctUntilChanged()
            .onEach { onWallet(it) }
    }

    fun flowOnTokenList(context: Context, accountModel: SolanaAccountModel): Flow<List<TokenModel>> {
        return flow {
            emit(
                listOf(
                    TokenModel(
                        tokenName = "Solana",
                        tokenSymbol = "SOL",
                        accountModel.solanaBalance,
                        R.drawable.ic_sol
                    )
                )
            )
        }.filterNotNull()
            .distinctUntilChanged()
            .onEach {
                onTokenList(
                    context = context,
                    tokens = it
                )
            }
    }

    private fun onWallet(solanaAccount: SolanaAccountModel?) {
        solanaAccount?.let {
            uiState = uiState.copy(currentAddress = solanaAccount.publicKey)
        }
    }

    private suspend fun onTokenList(context: Context, tokens: List<TokenModel>) {
        val quote = GetAccountBalanceUseCase.getUSDQuote(
            context = context,
            symbol = "SOL",
            slug = "solana",
        ) ?: 0f

        val solanaBalance = tokens.find {
            it.tokenSymbol == "SOL"
        }?.tokenBalance ?: 0f

        uiState = uiState.copy(
            isLoading = false,
            hasError = false,
            currentTotalBalance = quote * solanaBalance,
            tokens = tokens
        )
    }
}