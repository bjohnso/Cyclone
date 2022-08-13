package com.demo.cyclone.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.cyclone.entity.TokenTransferEntity
import com.demo.cyclone.ui.models.SolanaAccountModel
import com.demo.cyclone.ui.state.TransactionSelectAmountUiState
import com.demo.cyclone.usecase.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TransactionSelectAmountViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = TransactionSelectAmountUiState(),
        policy = neverEqualPolicy()
    )
        private set

    fun flowOnTransaction(context: Context): Flow<TokenTransferEntity?> {
        return flow {
            emit (
                GetTransactionUseCase.getLatestTransaction(context)
            )
        }.distinctUntilChanged()
    }

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
    }

    fun getDisplayTokenAmount(): String {
        return if (uiState.tokenAmount == 0f) {
            ""
        } else uiState.tokenAmount.toString()
    }

    fun getDisplayUsdAmount(): String {
        return String.format("%.2f", uiState.usdAmount)
    }

    fun updateTransaction(transferEntity: TokenTransferEntity?) {
        uiState.recipientAddress = transferEntity?.recipient ?: ""
        uiState = uiState
    }

    fun updateWallet(solanaAccountModel: SolanaAccountModel?) {
        uiState.walletBalance = solanaAccountModel?.solanaBalance ?: 0f
        uiState = uiState
    }

    fun updateTokenAmount(amount: String) {
        uiState.tokenAmount = amount.toFloatOrNull() ?: 0f
        uiState.usdAmount = uiState.tokenAmount * uiState.usdQuote
        uiState = uiState
    }

    fun getUSDQuote(context: Context) {
        viewModelScope.launch {
            uiState.usdQuote = GetQuoteUseCase.getUSDQuote(
                context = context,
                symbol = "SOL",
                slug = "solana",
            ) ?: 0f
            uiState.usdAmount = uiState.tokenAmount * uiState.usdQuote
            uiState = uiState
        }
    }
}