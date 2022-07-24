package com.demo.touchwallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demo.touchwallet.interfaces.NavigatorInterface
import com.demo.touchwallet.ui.composable.accounts.ImportAccountScreen
import com.demo.touchwallet.ui.composable.seedphrase.SeedCreationScreen
import com.demo.touchwallet.ui.composable.seedphrase.SeedPhraseRecoveryParams
import com.demo.touchwallet.ui.composable.seedphrase.SeedPhraseRecoveryScreen
import com.demo.touchwallet.ui.composable.wallet.WalletInitialisationScreen
import com.demo.touchwallet.ui.composable.wallet.WalletScreen
import com.demo.touchwallet.ui.navigation.Screen
import com.demo.touchwallet.ui.theme.TouchWalletTheme

class TouchActivity : ComponentActivity(), NavigatorInterface {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberNavController()

            TouchWalletTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.WalletScreen.route
                    ) {
                        composable(route = Screen.WalletInitialisationScreen.route) {
                            WalletInitialisationScreen(window, this@TouchActivity)
                        }
                        composable(
                            route = Screen.WalletScreen.route,
                            arguments = Screen.WalletScreen.arguments ?: listOf()
                        ) {
                            WalletScreen(
                                it.arguments?.getString(
                                    Screen.WalletScreen.Arguments.wallet_address.name
                                ) ?: "",
                                window = window,
                                this@TouchActivity
                            )
                        }
                        composable(route = Screen.SeedPhraseCreationScreen.route) {
                            SeedCreationScreen(window, this@TouchActivity)
                        }
                        composable(route = Screen.SeedPhraseRecoveryScreen.route) {
                            SeedPhraseRecoveryScreen(window, this@TouchActivity)
                        }
                        composable(route = Screen.ImportAccountsScreen.route) {
                            ImportAccountScreen(window, this@TouchActivity)
                        }
                    }
                }
            }
        }
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun navigateBack(destinationId: Int?, inclusive: Boolean?) {
        if (destinationId != null && inclusive != null)
            navController.popBackStack(destinationId, inclusive)
        else
            navController.popBackStack()
    }

    override fun navigate(route: String, navOptions: NavOptions?, navigatorExtras: Navigator.Extras?) {
        navController.navigate(
            route = route,
            navOptions = navOptions,
            navigatorExtras = navigatorExtras
        )
    }
}