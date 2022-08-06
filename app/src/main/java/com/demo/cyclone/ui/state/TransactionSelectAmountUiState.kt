package com.demo.cyclone.ui.state

data class TransactionSelectAmountUiState(
    override var isLoading: Boolean = true,
    override var hasError: Boolean = false,
    var tokenAmount: Float = 0f,
    var usdAmount: Float = 0f,
    var walletBalance: Float = 0f,
    var usdQuote: Float = 0f
): BaseUiState
