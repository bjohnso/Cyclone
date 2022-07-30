package com.demo.cyclone.ui.composable.seedphrase

import android.content.pm.ActivityInfo
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.demo.cyclone.R
import com.demo.cyclone.extensions.ConfigurationExtensions.heightPercentageDP
import com.demo.cyclone.extensions.ConfigurationExtensions.widthPercentageDP
import com.demo.cyclone.extensions.ContextExtensions.activity
import com.demo.cyclone.interfaces.NavigatorInterface
import com.demo.cyclone.ui.composable.shared.LockScreenOrientation
import com.demo.cyclone.ui.composable.shared.Spinner
import com.demo.cyclone.ui.composable.shared.SystemUi
import com.demo.cyclone.usecase.CreateWalletUseCase
import com.demo.cyclone.viewmodel.SeedCreationViewModel

@Composable
fun SeedCreationScreen(window: Window, navigatorInterface: NavigatorInterface) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val viewModel = ViewModelProvider(
        LocalContext.current.activity() as ViewModelStoreOwner
    )[SeedCreationViewModel::class.java]

    val createSeed by rememberUpdatedState(newValue = suspend {
        CreateWalletUseCase.createSeed(context = context)
    })

    viewModel.flowStateOnSeedWords(LocalContext.current)
        .collectAsState(initial = null)

    LaunchedEffect(true) {
        createSeed()
    }

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
                    start = configuration.widthPercentageDP(5f),
                    end = configuration.widthPercentageDP(5f),
                    bottom = configuration.heightPercentageDP(2f),
                )
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Title()
            SubTitle()

            when {
                viewModel.uiState.isLoading -> Spinner()
                viewModel.uiState.seedWords != null -> SeedPhraseItemGrid(
                    viewModel.uiState.seedWords ?: listOf(),
                    "#1A1A1A".toColorInt()
                )
            }

            CopyToClipboard()
            OKButton()
        }
    }
}

@Composable
private fun Title(navigatorInterface: NavigatorInterface? = null) {
    val configuration = LocalConfiguration.current

    Text(
        text = "Secret Recovery Phrase",
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
        text = "This is the only way you will be able to recover your account. Please store it somewhere safe!",
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
private fun CopyToClipboard() {
    val configuration = LocalConfiguration.current

    Row(
        modifier = Modifier
            .padding(
                top = configuration.heightPercentageDP(5f),
                start = configuration.widthPercentageDP(5f),
                end = configuration.widthPercentageDP(5f),
                bottom = configuration.heightPercentageDP(2f)
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_copy),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(end = configuration.widthPercentageDP(1f),)
        )

        Text(
            text = "Copy to clipboard",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = configuration.widthPercentageDP(1f),)
        )
    }
}

@Composable
private fun OKButton(navigatorInterface: NavigatorInterface? = null) {
    val configuration = LocalConfiguration.current

    Button(
        onClick = {

        },
        modifier = Modifier
            .padding(
                top = configuration.heightPercentageDP(2f),
                start = configuration.widthPercentageDP(5f),
                end = configuration.widthPercentageDP(5f),
                bottom = configuration.heightPercentageDP(2f)
            )
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color("#0AB9EE".toColorInt())),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = "OK, got it!",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}