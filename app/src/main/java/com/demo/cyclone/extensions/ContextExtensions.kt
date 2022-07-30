package com.demo.cyclone.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import com.demo.cyclone.CycloneApplication

object ContextExtensions {
    fun Context.touchWalletApplication(): CycloneApplication =
        applicationContext as CycloneApplication

    tailrec fun Context.activity(): Activity? = when (this) {
        is Activity -> this
        else -> (this as? ContextWrapper)?.baseContext?.activity()
    }
}