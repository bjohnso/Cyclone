package com.demo.cyclone.usecase

import android.content.Context
import com.demo.cyclone.repository.AccountRepository
import com.demo.cyclone.repository.TokenTransferRepository

object CreateTransactionUseCase {

    suspend fun createTransaction(context: Context, recipient: String): Boolean {
        val wallet = AccountRepository
            .getInstance()
            .getDefaultWallet(context)

        return if (wallet != null) {
            TokenTransferRepository
                .getInstance(context)
                .newTokenTransfer(
                    recipient = recipient,
                    sender = wallet
                )

            true
        } else false
    }
}