package com.demo.cyclone

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
import com.demo.cyclone.interfaces.NavigatorInterface
import com.demo.cyclone.ui.composable.accounts.ImportAccountScreen
import com.demo.cyclone.ui.composable.seedphrase.SeedCreationScreen
import com.demo.cyclone.ui.composable.seedphrase.SeedPhraseRecoveryScreen
import com.demo.cyclone.ui.composable.startup.SplashScreen
import com.demo.cyclone.ui.composable.transaction.TransactionSelectAddress
import com.demo.cyclone.ui.composable.transaction.TransactionSelectAmountScreen
import com.demo.cyclone.ui.composable.wallet.WalletCreateScreen
import com.demo.cyclone.ui.composable.wallet.WalletScreen
import com.demo.cyclone.ui.navigation.Screen
import com.demo.cyclone.ui.theme.TouchWalletTheme
import com.demo.cyclone.usecase.RetrieveSeedUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CycloneActivity : ComponentActivity(), NavigatorInterface {

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
                        startDestination = Screen.TransactionSelectAmountScreen.route
                    ) {
                        composable(route = Screen.SplashScreen.route) {
                            SplashScreen(
                                window = window,
                                navigatorInterface = this@CycloneActivity
                            )
                        }
                        composable(route = Screen.WalletCreateScreen.route) {
                            WalletCreateScreen(
                                window = window,
                                navigatorInterface = this@CycloneActivity
                            )
                        }
                        composable(
                            route = Screen.WalletScreen.route,
                            arguments = Screen.WalletScreen.arguments ?: listOf()
                        ) {
                            WalletScreen(
                                window = window,
                                navigatorInterface = this@CycloneActivity
                            )
                        }
                        composable(route = Screen.TransactionSelectAddressScreen.route) {
                            TransactionSelectAddress(
                                window = window,
                                navigatorInterface = this@CycloneActivity
                            )
                        }
                        composable(route = Screen.TransactionSelectAmountScreen.route) {
                            TransactionSelectAmountScreen(
                                window = window,
                                navigatorInterface = this@CycloneActivity
                            )
                        }
                        composable(route = Screen.SeedPhraseCreationScreen.route) {
                            SeedCreationScreen(
                                window = window,
                                navigatorInterface = this@CycloneActivity
                            )
                        }
                        composable(route = Screen.SeedPhraseRecoveryScreen.route) {
                            SeedPhraseRecoveryScreen(
                                window = window,
                                navigatorInterface = this@CycloneActivity
                            )
                        }
                        composable(route = Screen.ImportAccountsScreen.route) {
                            ImportAccountScreen(
                                window = window,
                                navigatorInterface = this@CycloneActivity
                            )
                        }
                    }
                }
            }

//            onStartUp()
        }
    }

    private fun onStartUp() {
        CoroutineScope(Dispatchers.Main).launch {
            val seed = RetrieveSeedUseCase
                .retrieveCurrentSeed(this@CycloneActivity)

            delay(1000)

            val route = when (seed?.seed) {
                null -> Screen.WalletCreateScreen.route
                else -> Screen.WalletScreen.route
            }

            val navOptions = NavOptions
                .Builder()
                .setPopUpTo(
                    route = Screen.SplashScreen.route,
                    inclusive = true
                ).build()

            navController.navigate(route, navOptions)
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