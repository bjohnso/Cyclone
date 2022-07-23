package com.demo.touchwallet.extensions

import java.util.*
import kotlin.experimental.and

object ByteExtensions {
    fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }

    fun BitSet.toBinaryString(startIndex: Int, endIndex: Int? = null): String? {
        var binaryString = ""

        for (i in startIndex..(endIndex ?: this.length() - 1)) {
            binaryString += when(this[i]) {
                true -> '1'
                else -> '0'
            }
        }

        return binaryString.ifBlank { null }
    }
}