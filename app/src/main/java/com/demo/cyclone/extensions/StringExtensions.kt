package com.demo.cyclone.extensions

import java.util.*

object StringExtensions {
    fun String.decodeHex(): ByteArray {
        check(length % 2 == 0) { "Must have an even length" }

        return chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
    }

    fun String.decodeBinaryString(): ByteArray {
        return BitSet(this.length).apply {
            for (i in this@decodeBinaryString.indices) {
                this[i] = when(this@decodeBinaryString[i]) {
                    '1' -> true
                    else -> false
                }
            }
        }.toByteArray()
    }
}