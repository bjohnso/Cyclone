package com.demo.touchwallet.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.touchwallet.repository.SolanaRepository
import com.demo.touchwallet.ui.models.AccountModel
import com.demo.touchwallet.ui.state.ImportAccountsUiState
import com.demo.touchwallet.crypto.Base58Encoder
import kotlinx.coroutines.flow.*
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters

class ImportAccountsViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = ImportAccountsUiState(),
        policy = neverEqualPolicy()
    )
        private set

    fun flowStateOnDeriveAddresses(context: Context): Flow<ImportAccountsUiState?> {
        return SolanaRepository
            .getInstance(context = context)
            .flowOnDeriveAccounts()
            .filterNotNull()
            .distinctUntilChanged()
            .map {
                uiState = uiState.copy(
                    isLoading = false,
                    hasError = false,
                    accounts = it.map { pair ->
                        val publicKey = (pair.public as Ed25519PublicKeyParameters).encoded
                        AccountModel(
                            publicKey = Base58Encoder.invoke(publicKey),
                            solBalance = 0f
                        )
                    }
                )
                uiState
            }
    }
}