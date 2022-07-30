package com.demo.cyclone.extensions

import androidx.compose.ui.graphics.Color

object ColorExtensions {
    val String.color get() = Color(android.graphics.Color.parseColor(this))
}