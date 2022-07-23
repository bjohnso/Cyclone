package com.demo.touchwallet.ui.state

import com.demo.touchwallet.ui.models.AccountModel

data class ImportAccountsUiState(
    override var isLoading: Boolean = true,
    override var hasError: Boolean = false,
    var accounts: List<AccountModel>? = null
) : BaseUiState