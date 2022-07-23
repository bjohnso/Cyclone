package com.demo.touchwallet.ui.composable.accounts

import android.content.pm.ActivityInfo
import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.demo.touchwallet.extensions.ConfigurationExtensions.heightPercentageDP
import com.demo.touchwallet.extensions.ConfigurationExtensions.widthPercentageDP
import com.demo.touchwallet.extensions.ContextExtensions.activity
import com.demo.touchwallet.interfaces.NavigatorInterface
import com.demo.touchwallet.ui.composable.shared.LockScreenOrientation
import com.demo.touchwallet.ui.composable.shared.SystemUi
import com.demo.touchwallet.ui.models.AccountModel
import com.demo.touchwallet.viewmodel.ImportAccountsViewModel

@Composable
fun ImportAccountScreen(window: Window, navigatorInterface: NavigatorInterface? = null) {
    val configuration = LocalConfiguration.current

    val viewModel = ViewModelProvider(
        LocalContext.current.activity() as ViewModelStoreOwner
    )[ImportAccountsViewModel::class.java]

    viewModel.flowStateOnDeriveAddresses(context = LocalContext.current)
        .collectAsState(initial = null)

    SystemUi(
        window = window,
        statusBarColor = "#222222".toColorInt(),
        navigationBarColor = "#222222".toColorInt(),
    )

    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

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
                .padding(
                    top = configuration.heightPercentageDP(10f),
                    bottom = configuration.heightPercentageDP(5f),
                )
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Title()
            SubTitle()
            if (viewModel.uiState.isLoading) {
                Spinner()
            } else if (viewModel.uiState.accounts != null) {
                AccountList(viewModel.uiState.accounts ?: listOf())
                ImportButton()
            }
        }
    }
}

@Composable
private fun Title(navigatorInterface: NavigatorInterface? = null) {
    val configuration = LocalConfiguration.current

    Text(
        text = "Import Accounts",
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold),
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
private fun SubTitle(navigatorInterface: NavigatorInterface? = null) {
    val configuration = LocalConfiguration.current

    Text(
        text = "Choose wallet accounts to import",
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = 18.sp, color = Color.LightGray, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(
                top = configuration.heightPercentageDP(2f),
                start = configuration.widthPercentageDP(5f),
                end = configuration.widthPercentageDP(5f),
                bottom = configuration.heightPercentageDP(5f)
            )
            .fillMaxWidth()
    )
}

@Composable
fun ColumnScope.AccountList(accounts: List<AccountModel>) {
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
            .clip(
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp,
                )
            )
    ) {
        items(
            items = accounts,
        ) { model ->
            AccountItem(model)
            Divider(thickness = .5.dp, color = Color("#A5A5A5".toColorInt()))
        }
    }
}

@Composable
fun AccountItem(account: AccountModel) {
    Row(
        modifier = Modifier
            .height(height = 50.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color("#1A1A1A".toColorInt()),
                        Color("#1A1A1A".toColorInt())
                    )
                )
            )
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        val keyDisplay =
            "${account.publicKey.take(4)}...${account.publicKey.takeLast(4)}"

        Text(
            text = keyDisplay,
            textAlign = TextAlign.Start,
            style = TextStyle(fontSize = 18.sp, color = Color("#A5A5A5".toColorInt())),
        )

        Spacer(modifier = Modifier.width(50.dp))
            
        Text(
            text = "${account.solBalance} SOL",
            textAlign = TextAlign.Start,
            style = TextStyle(fontSize = 18.sp, color = Color("#A5A5A5".toColorInt())),
        )
    }
}

@Composable
private fun ImportButton(navigatorInterface: NavigatorInterface? = null) {
    val configuration = LocalConfiguration.current

    Button(
        onClick = {

        },
        modifier = Modifier
            .padding(
                start = configuration.widthPercentageDP(5f),
                end = configuration.widthPercentageDP(5f),
                bottom = configuration.heightPercentageDP(2f)
            )
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color("#0AB9EE".toColorInt())),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = "Import Selected Accounts",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}

@Composable
private fun Spinner() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}