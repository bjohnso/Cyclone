package com.demo.touchwallet.ui.composable.shared

import android.view.View
import android.view.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.toColorInt
import com.demo.touchwallet.extensions.ContextExtensions.activity

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.activity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            activity.requestedOrientation = originalOrientation
        }
    }
}

@Composable
fun SystemUi(
    window: Window,
    statusBarColor: Int,
    navigationBarColor: Int
) = MaterialTheme {
    window.statusBarColor = statusBarColor
    window.navigationBarColor = navigationBarColor

    @Suppress("DEPRECATION")
    if (MaterialTheme.colors.surface.luminance() > 0.5f) {
        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    @Suppress("DEPRECATION")
    if (MaterialTheme.colors.surface.luminance() > 0.5f) {
        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
}