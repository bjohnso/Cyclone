package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.entity.KeyPairEntity
import com.demo.touchwallet.repository.SolanaRepository
import com.demo.touchwallet.repository.UserRepository

object RetrieveWalletUseCase {
    suspend fun retrieveCurrentWallet(context: Context): KeyPairEntity? {
        val currentAddress = UserRepository
            .getInstance()
            .getDefaultWallet(context = context)

        if (currentAddress.isNullOrBlank()) return null

        return SolanaRepository
            .getInstance(context = context)
            .retrieveKeyPair(address = currentAddress)
    }

    suspend fun retrieveWallet(address: String, context: Context): KeyPairEntity? {
        return SolanaRepository
            .getInstance(context = context)
            .retrieveKeyPair(address = address)
    }
}