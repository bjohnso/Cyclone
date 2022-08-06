package com.demo.cyclone.usecase

import android.content.Context
import com.demo.cyclone.repository.CMCRepository

object GetQuoteUseCase {
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