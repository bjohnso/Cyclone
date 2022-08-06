package com.demo.cyclone.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.demo.cyclone.ui.state.TransactionSelectAmountUiState

class TransactionSelectAmountViewModel: ViewModel() {
    var uiState by mutableStateOf(
        value = TransactionSelectAmountUiState(),
        policy = neverEqualPolicy()
    )
        private set
}