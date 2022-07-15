package com.demo.touchwallet.extensions

import java.util.*
import kotlin.experimental.and

object ByteExtensions {
    fun ByteArray.toInt(): Int {
        var result = 0
        var shift = 0
        for (byte in this) {
            result = result or (byte.toInt() shl shift)
            shift += 8
        }
        return result
    }

    fun ByteArray.toBitSet(): BitSet {
        val bits = BitSet()
        for (i in 0 until this.size * 8) {
            if (this[this.size - i / 8 - 1] and ((1 shl i % 8).toByte()) > 0) {
                bits.set(i)
            }
        }
        return bits
    }

    fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }
}