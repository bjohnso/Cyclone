package com.demo.touchwallet.ui.navigation

import androidx.navigation.*

sealed class Screen(val route: String, val arguments: List<NamedNavArgument>? = null) {
    object SplashScreen: Screen(route = "splash_screen")
    object WalletCreateScreen: Screen(route = "wallet_create_screen")
    object WalletScreen: Screen (route = "wallet_screen")
    object SeedPhraseCreationScreen: Screen(route = "seed_phrase_creation_screen")
    object SeedPhraseRecoveryScreen: Screen(route = "seed_phrase_recovery_screen")
    object ImportAccountsScreen: Screen(route = "import_accounts_screen")
}
