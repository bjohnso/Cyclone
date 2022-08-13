package com.demo.cyclone.ui.composable.wallet

import android.content.pm.ActivityInfo
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.demo.cyclone.extensions.ConfigurationExtensions.heightPercentageDP
import com.demo.cyclone.extensions.ConfigurationExtensions.widthPercentageDP
import com.demo.cyclone.extensions.ContextExtensions.activity
import com.demo.cyclone.interfaces.NavigatorInterface
import com.demo.cyclone.ui.composable.shared.LockScreenOrientation
import com.demo.cyclone.ui.composable.shared.Spinner
import com.demo.cyclone.ui.composable.shared.SystemUi
import com.demo.cyclone.ui.models.TokenModel
import com.demo.cyclone.ui.navigation.Screen
import com.demo.cyclone.viewmodel.WalletViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun WalletScreen(
    window: Window,
    navigatorInterface: NavigatorInterface? = null
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    val viewModel = ViewModelProvider(
        LocalContext.current.activity() as ViewModelStoreOwner
    )[WalletViewModel::class.java]

    val flowOnWallet by rememberUpdatedState(
        newValue = viewModel.flowOnWallet(
            context = context
        )
    )

    LaunchedEffect(true) {
        flowOnWallet.collect { account ->
            account?.let {
                viewModel.flowOnTokenList(
                    context = context,
                    accountModel = it
                ).collect()
            }
        }
    }
    
    SystemUi(
        window = window,
        statusBarColor = "#222222".toColorInt(),
        navigationBarColor = "#222222".toColorInt()
    )

    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    SwipeRefresh(
        state = rememberSwipeRefreshState(viewModel.uiState.isLoading),
        onRefresh = {
            viewModel.setLoading()

            coroutine.launch {
                flowOnWallet.collect { account ->
                    account?.let {
                        viewModel.flowOnTokenList(
                            context = context,
                            accountModel = it
                        ).collect()
                    }
                }
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color("#222222".toColorInt()),
                            Color("#222222".toColorInt()),
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top
            ) {
                WalletAddressTitle(viewModel.uiState.currentAddress ?: "")

                Spacer(
                    modifier = Modifier.padding(
                        top = configuration.heightPercentageDP(5f)
                    )
                )

                WalletTotalBalance(viewModel.uiState.currentTotalBalance)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(
                            start = configuration.widthPercentageDP(10f),
                            end = configuration.widthPercentageDP(10f)
                        )
                        .fillMaxWidth()
                ) {
                    WalletActionButton(text = "Deposit") {

                    }
                    WalletActionButton(text = "Send") {
                        navigatorInterface?.navigate(Screen.TransactionSelectAddressScreen.route)
                    }
                }

                Spacer(
                    modifier = Modifier.padding(
                        top = configuration.heightPercentageDP(5f)
                    )
                )

                TokenList(
                    tokenList = viewModel.uiState.tokens ?: listOf()
                )
            }
        }
    }
}

@Composable
fun ColumnScope.TokenList(tokenList: List<TokenModel>) {
    val configuration = LocalConfiguration.current
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f)
            .padding(
                start = configuration.widthPercentageDP(5f),
                end = configuration.widthPercentageDP(5f),
                bottom = configuration.heightPercentageDP(2f)
            )
    ) {
        items(
            items = tokenList,
        ) { model ->
            TokenItem(model)
            Spacer(modifier = Modifier.padding(bottom = 5.dp))
        }
    }
}

@Composable
fun TokenItem(token: TokenModel) {
    Surface(elevation = 10.dp) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color("#272727".toColorInt()),
                            Color("#272727".toColorInt())
                        )
                    )
                )
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp,
                    )
                )
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = Color("#1A1A1A".toColorInt()),
                        shape = RoundedCornerShape(40.dp)
                    )
            ) {
                Image(
                    painter = painterResource(id = token.tokenImageResourceId),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(Modifier.padding(start = 20.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = token.tokenName,
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                )

                Text(
                    text = "${token.tokenBalance} ${token.tokenSymbol}",
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color("#A5A5A5".toColorInt()),
                        fontWeight = FontWeight.Medium
                    ),
                )
            }
        }
    }
}

@Composable
fun WalletAddressTitle(walletAddress: String) {
    val configuration = LocalConfiguration.current

    val keyDisplay =
        "${walletAddress.take(4)}...${walletAddress.takeLast(4)}"

    Text(
        text = keyDisplay,
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = 18.sp, color = Color("#A5A5A5".toColorInt()), fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(
                top = configuration.heightPercentageDP(2f),
                start = configuration.widthPercentageDP(5f),
                end = configuration.widthPercentageDP(5f),
                bottom = configuration.heightPercentageDP(2f)
            )
            .fillMaxWidth()
    )
}

@Composable
fun WalletTotalBalance(totalWalletBalance: Float) {
    val configuration = LocalConfiguration.current

    Text(
        text = "$${totalWalletBalance}",
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = 50.sp, color = Color.White, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(
                top = configuration.heightPercentageDP(2f),
                start = configuration.widthPercentageDP(5f),
                end = configuration.widthPercentageDP(5f),
                bottom = configuration.heightPercentageDP(2f)
            )
            .fillMaxWidth()
    )
}

@Composable
private fun RowScope.WalletActionButton(text: String, onClick:() -> Unit) {
    val configuration = LocalConfiguration.current

    Button(
        onClick = {
            onClick.invoke()
        },
        modifier = Modifier
            .padding(
                start = configuration.widthPercentageDP(2f),
                end = configuration.widthPercentageDP(2f),
                bottom = configuration.heightPercentageDP(2f)
            )
            .weight(1f)
            .fillMaxWidth()
            .aspectRatio(3f),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color("#0AB9EE".toColorInt())),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}