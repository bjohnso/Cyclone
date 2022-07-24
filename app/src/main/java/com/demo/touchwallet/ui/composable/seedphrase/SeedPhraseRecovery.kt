package com.demo.touchwallet.ui.composable.seedphrase

import android.content.pm.ActivityInfo
import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demo.touchwallet.extensions.ConfigurationExtensions.heightPercentageDP
import com.demo.touchwallet.extensions.ConfigurationExtensions.widthPercentageDP
import com.demo.touchwallet.interfaces.KeyboardActionsInterface
import com.demo.touchwallet.interfaces.NavigatorInterface
import com.demo.touchwallet.ui.composable.shared.LockScreenOrientation
import com.demo.touchwallet.ui.composable.shared.SystemUi
import com.demo.touchwallet.ui.navigation.Screen
import com.demo.touchwallet.usecase.DecodeMnemonicUseCase
import com.demo.touchwallet.viewmodel.SeedPhraseRecoveryViewModel
import kotlinx.coroutines.launch

@Composable
fun SeedPhraseRecoveryScreen(window: Window? = null, navigatorInterface: NavigatorInterface? = null) {
    val configuration = LocalConfiguration.current
    val viewModel: SeedPhraseRecoveryViewModel = viewModel()

    val coroutineScope = rememberCoroutineScope()

    val keyboardActionsInterface = object: KeyboardActionsInterface {
        override fun onNext(text: String?) {
            viewModel.funIncrementIndex()
        }

        override fun onGo(text: String?) {

        }

        override fun onDone(text: String?) {

        }

    }

    window?.let {
        SystemUi(
            window = it,
            statusBarColor = "#222222".toColorInt(),
            navigationBarColor = "#222222".toColorInt(),
        )
    }

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (viewModel.uiState.currentIndex > 0) {
                    PreviousButton(onClick = {
                        viewModel.funDecrementIndex()
                    })
                    Spacer(modifier = Modifier.size(configuration.widthPercentageDP(5f)))
                }

                SeedPhraseItem(
                    index = viewModel.uiState.currentIndex,
                    word = viewModel.uiState.seedWords[viewModel.uiState.currentIndex],
                    itemColor = "#1A1A1A".toColorInt(),
                    height = configuration.heightPercentageDP(8f),
                    isEditable = true,
                    imeiAction = ImeAction.Next,
                    keyboardActionsInterface = keyboardActionsInterface,
                    onWordChange = { word -> viewModel.updateWordAtCurrentIndex(word) }
                )
            }

            if (
                DecodeMnemonicUseCase.isValid12WordMnemonic(
                    viewModel.uiState.seedWords
                )
            ) {
                val context = LocalContext.current
                DoneButton {
                    coroutineScope.launch {
                        if (
                            DecodeMnemonicUseCase.decodeMnemonic(
                                seedWords = viewModel.uiState.seedWords,
                                context = context
                            )
                        ) {
                            navigatorInterface?.navigate(
                                Screen.ImportAccountsScreen.route
                            )
                        }
                    }
                }
            }
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
        text = "Restore an existing wallet with your 12 or 24-word secret recovery phrase",
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
private fun PreviousButton(onClick: () -> Unit) {
    val configuration = LocalConfiguration.current

    IconButton(
        onClick = {
            onClick.invoke()
        },
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(Color("#AC22E7".toColorInt()))
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            tint = Color("#222222".toColorInt()),
            modifier = Modifier.size(configuration.heightPercentageDP(10f)),
            contentDescription = "",
        )
    }
}

@Composable
private fun DoneButton(onClick: () -> Unit) {
    val configuration = LocalConfiguration.current

    Button(
        onClick = {
            onClick.invoke()
        },
        modifier = Modifier
            .padding(
                top = configuration.heightPercentageDP(5f),
                start = configuration.widthPercentageDP(5f),
                end = configuration.widthPercentageDP(5f),
                bottom = configuration.heightPercentageDP(2f)
            )
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color("#0AB9EE".toColorInt())),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = "I'm done",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}