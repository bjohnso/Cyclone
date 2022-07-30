package com.demo.cyclone.extensions

object ExceptionExtensions {
    fun <T> tryOrDefault(defaultValue: T, f: () -> T): T {
        return try {
            f()
        } catch (e: Exception) {
            defaultValue
        }
    }

    suspend fun <T> tryOrDefaultAsync(defaultValue: T, f: suspend () -> T): T {
        return try {
            f()
        } catch (e: Exception) {
            defaultValue
        }
    }
}