package com.demo.cyclone.repository

import android.content.Context
import com.demo.cyclone.database.CycloneDatabase
import com.demo.cyclone.entity.TokenTransferEntity
import com.demo.cyclone.extensions.ContextExtensions.touchWalletApplication

class TokenTransferRepository(context: Context) {
    private var db = CycloneDatabase(context.touchWalletApplication())

    suspend fun newTokenTransfer(recipient: String, sender: String): TokenTransferEntity? {
        val tokenTransfer = TokenTransferEntity(
            recipient = recipient,
            sender = sender,
            createdAt = System.currentTimeMillis()
        )

        db.tokenTransferDao().persistTokenTransfer(tokenTransfer)
        return tokenTransfer
    }

    companion object {
        private var instance: TokenTransferRepository? = null

        fun getInstance(context: Context): TokenTransferRepository {
            return instance ?: TokenTransferRepository(context).also {
                instance = it
            }
        }
    }
}