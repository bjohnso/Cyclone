package com.demo.touchwallet.ui.state

import com.demo.touchwallet.ui.models.SolanaAccountModel

data class ImportAccountsUiState(
    override var isLoading: Boolean = true,
    override var hasError: Boolean = false,
    var accounts: List<SolanaAccountModel>? = null
) : BaseUiState