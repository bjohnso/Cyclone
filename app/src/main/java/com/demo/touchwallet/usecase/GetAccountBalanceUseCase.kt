package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.repository.SolanaRPCRepository
import com.demo.touchwallet.ui.models.SolanaAccountModel

object GetAccountBalanceUseCase {
    suspend fun getAccountBalance(
        pubKey: String,
        context: Context
    ): SolanaAccountModel {
        val solanaRepository = SolanaRPCRepository.getInstance()

        val balance = solanaRepository.getBalance(
            pubKey = pubKey, context = context
        )

        return SolanaAccountModel(
            publicKey = pubKey,
            balance = balance?.result?.value ?: 0f
        )
    }
}