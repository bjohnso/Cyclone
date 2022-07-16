package com.demo.touchwallet.ui.composable.wallet

import android.content.pm.ActivityInfo
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.demo.touchwallet.R
import com.demo.touchwallet.interfaces.NavigatorInterface
import com.demo.touchwallet.ui.composable.shared.LockScreenOrientation
import com.demo.touchwallet.ui.composable.shared.SystemUi
import com.demo.touchwallet.ui.navigation.Screen

@Composable
fun WalletInit(window: Window, navigatorInterface: NavigatorInterface? = null) {
    SystemUi(
        window = window,
        statusBarColor = "#241070".toColorInt(),
        navigationBarColor = "#120838".toColorInt()
    )

    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color("#241070".toColorInt()),
                        Color("#120838".toColorInt())
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 50.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            ImageView(navigatorInterface = navigatorInterface)
//            MarketingCopy(navigatorInterface = navigatorInterface)
            CreateWalletButton(navigatorInterface = navigatorInterface)
            ImportWalletText(navigatorInterface = navigatorInterface)
        }
    }
}

@Composable
private fun ImageView(navigatorInterface: NavigatorInterface? = null) {
    Image(
        painter = painterResource(id = R.drawable.ic_splash_logo),
        contentDescription = "",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .padding(top = 50.dp, start = 30.dp, end = 30.dp)
    )
}

@Composable
private fun MarketingCopy(navigatorInterface: NavigatorInterface? = null) {
    Text(
        text = "A wallet built for cryptographically secure transactions while offline",
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = 24.sp, color = Color.White, fontStyle = FontStyle.Italic),
        modifier = Modifier
            .padding(top = 10.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun CreateWalletButton(navigatorInterface: NavigatorInterface? = null) {
    Button(
        onClick = {
            navigatorInterface?.navigate(Screen.WalletSeedCreation.route)
        },
        modifier = Modifier
            .padding(top = 50.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color("#0AB9EE".toColorInt())),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = "Create a new wallet",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}

@Composable
private fun ImportWalletText(navigatorInterface: NavigatorInterface? = null) {
    ClickableText(
        text = AnnotatedString(text = "Import existing wallet"),
        style = TextStyle(
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        ),
        onClick = {},
        modifier = Modifier
            .padding(top = 30.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth(),
    )
}
