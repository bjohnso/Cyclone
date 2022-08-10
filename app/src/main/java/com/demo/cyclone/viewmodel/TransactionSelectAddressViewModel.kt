package com.demo.cyclone.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.cyclone.ui.state.TransactionSelectAddressUiState

class TransactionSelectAddressViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = TransactionSelectAddressUiState(),
        policy = neverEqualPolicy()
    )
        private set

    fun updateAddress(address: String) {
        uiState.recipientAddress = address
        uiState = uiState
    }
}