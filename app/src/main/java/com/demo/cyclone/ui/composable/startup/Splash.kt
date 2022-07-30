package com.demo.cyclone.ui.composable.startup

import android.content.pm.ActivityInfo
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.demo.cyclone.R
import com.demo.cyclone.interfaces.NavigatorInterface
import com.demo.cyclone.ui.composable.shared.LockScreenOrientation
import com.demo.cyclone.ui.composable.shared.SystemUi

@Composable
fun SplashScreen(window: Window, navigatorInterface: NavigatorInterface? = null) {
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
            verticalArrangement = Arrangement.Top
        ) {
            ImageView(navigatorInterface = navigatorInterface)
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