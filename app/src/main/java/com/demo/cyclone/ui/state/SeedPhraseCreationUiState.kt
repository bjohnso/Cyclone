package com.demo.cyclone.ui.state

data class SeedPhraseCreationUiState(
    override var isLoading: Boolean = true,
    override var hasError: Boolean = false,
    var seedWords: List<String> ? = null,
    ) : BaseUiState
