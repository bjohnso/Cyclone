package com.demo.touchwallet.extensions

object NumberExtensions {
    fun Float.toPrecision(precision: Int) =
        "%.${precision}f".format(this).toFloat()
}