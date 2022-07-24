package com.demo.touchwallet.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.touchwallet.ui.state.ImportAccountsUiState
import com.demo.touchwallet.ui.models.SolanaAccountModel
import com.demo.touchwallet.usecase.DeriveAccountsUseCase
import kotlinx.coroutines.flow.*

class ImportAccountsViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = ImportAccountsUiState(),
        policy = neverEqualPolicy()
    )
        private set

    fun flowOnDerive(context: Context): Flow<List<SolanaAccountModel>?> {
        return flow {
            emit(DeriveAccountsUseCase.deriveAccounts(context = context))
        }.distinctUntilChanged()
            .onEach { onDeriveAddresses(it) }
    }

    private fun onDeriveAddresses(accounts: List<SolanaAccountModel>?) {
        if (!accounts.isNullOrEmpty()) {
            uiState = uiState.copy(
                isLoading = false,
                hasError = false,
                accounts = accounts
            )
        }
    }
}