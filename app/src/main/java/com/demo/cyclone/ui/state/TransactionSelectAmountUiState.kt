package com.demo.cyclone.ui.state

data class TransactionSelectAmountUiState(
    override var isLoading: Boolean = true,
    override var hasError: Boolean = false
): BaseUiState
