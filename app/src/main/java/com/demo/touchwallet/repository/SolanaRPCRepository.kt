package com.demo.touchwallet.repository

import android.content.Context
import com.demo.touchwallet.api.ApiFactory
import com.demo.touchwallet.api.requestdto.RPCGetBalanceRequestDTO
import com.demo.touchwallet.extensions.ExceptionExtensions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SolanaRPCRepository {
    suspend fun getBalance(pubKey: String, context: Context) = withContext(Dispatchers.IO) {
        return@withContext ExceptionExtensions.tryOrDefaultAsync(null) {
            return@tryOrDefaultAsync ApiFactory
                .getApiClient(context = context)
                .getBalance(
                    RPCGetBalanceRequestDTO(params = listOf(pubKey))
                ).execute()
        }?.body()
    }

    companion object {
        private var instance: SolanaRPCRepository? = null

        fun getInstance(): SolanaRPCRepository {
            return instance ?: SolanaRPCRepository().also {
                instance = it
            }
        }
    }
}