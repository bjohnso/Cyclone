package com.demo.touchwallet.ui.composable.wallet

import android.content.pm.ActivityInfo
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.demo.touchwallet.R
import com.demo.touchwallet.interfaces.NavigatorInterface
import com.demo.touchwallet.ui.composable.seedphrase.SeedPhraseItemGrid
import com.demo.touchwallet.ui.composable.shared.LockScreenOrientation
import com.demo.touchwallet.ui.composable.shared.SystemUi

@Composable
fun WalletSeedCreation(window: Window, navigatorInterface: NavigatorInterface) {
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
                .padding(bottom = 50.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Title()
            SubTitle()
            SeedPhraseItemGrid("#1A1A1A".toColorInt())
            CopyToClipboard()
            OKButton()
        }
    }
}

@Composable
private fun Title(navigatorInterface: NavigatorInterface? = null) {
    Text(
        text = "Secret Recovery Phrase",
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(top = 10.dp, start = 30.dp, end = 30.dp, bottom = 30.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun SubTitle(navigatorInterface: NavigatorInterface? = null) {
    Text(
        text = "This is the only way you will be able to recover your account. Please store it somewhere safe!",
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = 18.sp, color = Color.LightGray, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(top = 10.dp, start = 30.dp, end = 30.dp, bottom = 50.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun CopyToClipboard() {
    Row(
        modifier = Modifier
            .padding(top = 30.dp, start = 30.dp, end = 30.dp, bottom = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_copy),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(end = 5.dp)
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
                .padding(start = 5.dp)
        )
    }
}

@Composable
private fun OKButton(navigatorInterface: NavigatorInterface? = null) {
    Button(
        onClick = {

        },
        modifier = Modifier
            .padding(top = 50.dp, start = 30.dp, end = 30.dp)
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