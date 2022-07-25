package com.demo.touchwallet.repository

import android.content.Context
import com.demo.touchwallet.api.factory.CMCFactory
import com.demo.touchwallet.extensions.ExceptionExtensions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CMCRepository {
    suspend fun getQuote(symbol: String, context: Context) = withContext(Dispatchers.IO) {
        return@withContext ExceptionExtensions.tryOrDefaultAsync(null) {
            return@tryOrDefaultAsync CMCFactory
                .getApiClient(context = context)
                .getQuote(symbol = symbol)
                .execute()
        }?.body()
    }

    companion object {
        private var instance: CMCRepository? = null

        fun getInstance(): CMCRepository {
            return instance ?: CMCRepository().also {
                instance = it
            }
        }
    }
}