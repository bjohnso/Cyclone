package com.demo.cyclone.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.cyclone.extensions.ExceptionExtensions
import com.demo.cyclone.ui.state.TransactionSelectAmountUiState
import com.demo.cyclone.usecase.GetQuoteUseCase
import kotlinx.coroutines.launch

class TransactionSelectAmountViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = TransactionSelectAmountUiState(),
        policy = neverEqualPolicy()
    )
        private set

    fun getDisplayTokenAmount(): String {
        return if (uiState.tokenAmount == 0f) {
            ""
        } else uiState.tokenAmount.toString()
    }

    fun getDisplayUsdAmount(): String {
        return uiState.usdAmount.toString()
    }

    fun updateTokenAmount(amount: String) {
        uiState.tokenAmount = amount.trimEnd('.').toFloatOrNull() ?: 0f

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