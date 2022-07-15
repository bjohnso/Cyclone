package com.demo.touchwallet.ui.navigation

sealed class Screen(val route: String) {
    object WalletInitScreen: Screen(route = "wallet_init")
}
