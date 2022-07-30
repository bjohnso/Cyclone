package com.demo.cyclone.ui.state

data class TransactionSelectAddressUiState(
    override var isLoading: Boolean = true,
    override var hasError: Boolean = false,
    var recipientAddress: String = ""
): BaseUiState
