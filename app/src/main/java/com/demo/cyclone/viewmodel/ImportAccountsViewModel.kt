package com.demo.cyclone.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.cyclone.ui.state.ImportAccountsUiState
import com.demo.cyclone.ui.models.SolanaAccountModel
import com.demo.cyclone.usecase.DeriveAccountsUseCase
import com.demo.cyclone.usecase.GetAccountBalanceUseCase
import kotlinx.coroutines.flow.*

class ImportAccountsViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = ImportAccountsUiState(),
        policy = neverEqualPolicy()
    )
        private set

    fun flowOnDerive(context: Context): Flow<SolanaAccountModel?> {
        return flow {
            val pubKeys = DeriveAccountsUseCase.deriveAccounts(
                context = context
            )

            pubKeys?.forEach {
                emit(
                    GetAccountBalanceUseCase.getAccountBalance(
                        context = context, pubKey = it
                    )
                )
            }

            uiState = uiState.copy(
                isLoading = false,
                hasError = false,
            )
        }.distinctUntilChanged()
            .onEach { onDeriveAddresses(it) }
    }

    private fun onDeriveAddresses(account: SolanaAccountModel?) {
        if (account != null) {
            val accounts = (uiState.accounts ?: listOf()) + listOf(account)
            uiState = uiState.copy(
                isLoading = false,
                hasError = false,
                accounts = accounts.distinctBy { it.publicKey }
            )
        }
    }
}