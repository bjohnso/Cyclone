package com.demo.touchwallet.ui.composable.wallet

import android.content.pm.ActivityInfo
import android.view.View
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.demo.touchwallet.R
import com.demo.touchwallet.extensions.ContextExtensions.activity

@Composable
fun WalletInit(windows: Window) {
    SystemUi(windows = windows)
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
            ImageView()
            MarketingCopy()
            CreateWalletButton()
            ImportWalletText()
        }
    }
}

@Composable
fun SystemUi(windows: Window) =
    MaterialTheme {
        windows.statusBarColor = "#241070".toColorInt()
        windows.navigationBarColor = "#120838".toColorInt()

        @Suppress("DEPRECATION")
        if (MaterialTheme.colors.surface.luminance() > 0.5f) {
            windows.decorView.systemUiVisibility = windows.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        @Suppress("DEPRECATION")
        if (MaterialTheme.colors.surface.luminance() > 0.5f) {
            windows.decorView.systemUiVisibility = windows.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

@Composable
private fun ImageView() {
    Image(
        painter = painterResource(id = R.drawable.ic_spash_logo),
        contentDescription = "",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .padding(top = 50.dp, start = 30.dp, end = 30.dp)
    )
}

@Composable
private fun MarketingCopy() {
    Text(
        text = "A wallet built for cryptographically secure transactions while offline",
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = 24.sp, color = Color.White),
        modifier = Modifier
            .padding(top = 10.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun CreateWalletButton() {
    Button(
        onClick = {},
        modifier = Modifier
            .padding(top = 50.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color("#0AB9EE".toColorInt())),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = "Create a new wallet",
            style = TextStyle(fontSize = 18.sp, color = Color.White),
        )
    }
}

@Composable
private fun ImportWalletText() {
    ClickableText(
        text = AnnotatedString(text = "Import existing wallet"),
        style = TextStyle(fontSize = 18.sp, color = Color.White, textAlign = TextAlign.Center),
        onClick = {},
        modifier = Modifier
            .padding(top = 30.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth(),
    )
}

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.activity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}
