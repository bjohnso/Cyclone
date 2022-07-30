package com.demo.cyclone.extensions

object NumberExtensions {
    fun Float.toPrecision(precision: Int) =
        "%.${precision}f".format(this).toFloat()
}