package com.demo.touchwallet.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import com.demo.touchwallet.TouchWalletApplication

object ContextExtensions {
    fun Context.touchWalletApplication(): TouchWalletApplication =
        applicationContext as TouchWalletApplication

    tailrec fun Context.activity(): Activity? = when (this) {
        is Activity -> this
        else -> (this as? ContextWrapper)?.baseContext?.activity()
    }
}