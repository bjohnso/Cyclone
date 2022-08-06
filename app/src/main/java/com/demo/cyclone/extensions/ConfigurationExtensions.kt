package com.demo.cyclone.extensions

import android.content.res.Configuration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object ConfigurationExtensions {
    fun Configuration.heightPercentageDP(percentage: Float): Dp =
        (this.screenHeightDp / 100 * percentage).dp

    fun Configuration.widthPercentageDP(percentage: Float): Dp =
        (this.screenWidthDp / 100 * percentage).dp

    fun Configuration.heightPercentageSP(percentage: Float): TextUnit =
        (this.screenHeightDp / 100 * percentage).sp

    fun Configuration.widthPercentageSP(percentage: Float): TextUnit =
        (this.screenWidthDp / 100 * percentage).sp
}