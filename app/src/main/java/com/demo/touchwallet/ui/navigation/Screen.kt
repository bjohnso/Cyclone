package com.demo.touchwallet.ui.navigation

import androidx.navigation.*

sealed class Screen(val route: String, val arguments: List<NamedNavArgument>? = null) {
    object WalletInitialisationScreen: Screen(route = "wallet_initialisation_screen")
    object WalletScreen: Screen (
        route = "wallet_screen/{${Arguments.wallet_address.name}}",
        arguments = listOf(
            navArgument(Arguments.wallet_address.name) { type = NavType.StringType }
        )
    ) {
        enum class Arguments {
            wallet_address
        }
    }
    object SeedPhraseCreationScreen: Screen(route = "seed_phrase_creation_screen")
    object SeedPhraseRecoveryScreen: Screen(route = "seed_phrase_recovery_screen")
    object ImportAccountsScreen: Screen(route = "import_accounts_screen")
}
