package com.demo.touchwallet.extensions

import android.content.res.Configuration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ConfigurationExtensions {
    fun Configuration.heightPercentageDP(percentage: Float): Dp =
        (this.screenHeightDp / 100 * percentage).dp

    fun Configuration.widthPercentageDP(percentage: Float): Dp =
        (this.screenWidthDp / 100 * percentage).dp
}