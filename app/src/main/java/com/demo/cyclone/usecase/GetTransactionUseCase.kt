package com.demo.cyclone.usecase

import android.content.Context
import com.demo.cyclone.entity.TokenTransferEntity
import com.demo.cyclone.repository.TokenTransferRepository

object GetTransactionUseCase {
    suspend fun getLatestTransaction(context: Context): TokenTransferEntity? {
        val tokenTransferRepository = TokenTransferRepository
            .getInstance(context)

        return RetrieveWalletUseCase.retrieveCurrentWallet(context)?.let {
            tokenTransferRepository.getTokenTransfer(it.publicKey).firstOrNull()
        }
    }
}