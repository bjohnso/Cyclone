package com.demo.cyclone.ui.state

import com.demo.cyclone.ui.models.SolanaAccountModel

data class ImportAccountsUiState(
    override var isLoading: Boolean = true,
    override var hasError: Boolean = false,
    var accounts: List<SolanaAccountModel>? = null
) : BaseUiState