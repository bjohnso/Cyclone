package com.demo.touchwallet.ui.state

data class SeedPhraseRecoveryUiState(
    var isLoading: Boolean = false,
    var hasError: Boolean = false,
    var currentWord: String = "",
    var currentIndex: Int = 0,
    val seedWords: MutableList<String> = MutableList(24) { "" }
)
