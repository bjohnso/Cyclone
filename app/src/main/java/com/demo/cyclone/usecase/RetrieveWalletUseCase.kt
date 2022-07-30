package com.demo.cyclone.usecase

import android.content.Context
import com.demo.cyclone.entity.KeyPairEntity
import com.demo.cyclone.repository.WalletRepository
import com.demo.cyclone.repository.AccountRepository

object RetrieveWalletUseCase {
    suspend fun retrieveCurrentWallet(context: Context): KeyPairEntity? {
        val currentAddress = AccountRepository
            .getInstance()
            .getDefaultWallet(context = context)

        if (currentAddress.isNullOrBlank()) return null

        return WalletRepository
            .getInstance(context = context)
            .retrieveKeyPair(address = currentAddress)
    }

    suspend fun retrieveWallet(address: String, context: Context): KeyPairEntity? {
        return WalletRepository
            .getInstance(context = context)
            .retrieveKeyPair(address = address)
    }
}