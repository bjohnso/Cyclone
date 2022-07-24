package com.demo.touchwallet.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.touchwallet.ui.models.AccountModel
import com.demo.touchwallet.ui.state.ImportAccountsUiState
import com.demo.touchwallet.entity.KeyPairEntity
import com.demo.touchwallet.usecase.DeriveAccountsUseCase
import kotlinx.coroutines.flow.*

class ImportAccountsViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = ImportAccountsUiState(),
        policy = neverEqualPolicy()
    )
        private set

    fun flowOnDerive(context: Context): Flow<List<KeyPairEntity>?> {
        return flow {
            emit(DeriveAccountsUseCase.deriveAccounts(context = context))
        }.distinctUntilChanged()
            .onEach { onDeriveAddresses(it) }
    }

    private fun onDeriveAddresses(keyPairs: List<KeyPairEntity>?) {
        if (!keyPairs.isNullOrEmpty()) {
            uiState = uiState.copy(
                isLoading = false,
                hasError = false,
                accounts = keyPairs.map { pair ->
                    AccountModel(
                        publicKey = pair.publicKey,
                        solBalance = 0f
                    )
                }
            )
        }
    }
}