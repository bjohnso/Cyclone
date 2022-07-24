package com.demo.touchwallet.ui.state

import com.demo.touchwallet.ui.models.TokenModel

data class WalletUiState(
    override var isLoading: Boolean = true,
    override var hasError: Boolean = false,
    var currentAddress: String? = null,
    var currentTotalBalance: Float = 0f,
    var tokens: List<TokenModel>? = null,
): BaseUiState
