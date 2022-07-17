package com.demo.touchwallet.ui.navigation

sealed class Screen(val route: String) {
    object WalletInitialisationScreen: Screen(route = "wallet_initialisation_screen")
    object SeedPhraseCreationScreen: Screen(route = "seed_phrase_creation_screen")
    object SeedPhraseRecoveryScreen: Screen(route = "seed+phrase_recovery_screen")
}
