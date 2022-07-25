package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.repository.CMCRepository
import com.demo.touchwallet.repository.SolanaRPCRepository
import com.demo.touchwallet.ui.models.SolanaAccountModel

object GetAccountBalanceUseCase {
    suspend fun getAccountBalance(
        context: Context,
        pubKey: String,
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

    suspend fun getUSDQuote(
        context: Context,
        symbol: String,
        slug: String? = null,
    ): Float? {
        val cmcRepository = CMCRepository.getInstance()

        val quote = cmcRepository.getQuote(
            symbol = symbol, context = context
        )

        return quote?.getQuoteUSD(
            symbol = symbol,
            slug = slug
        )
    }
}