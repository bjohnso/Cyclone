package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.entity.KeyPairEntity
import com.demo.touchwallet.repository.WalletRepository
import com.demo.touchwallet.repository.AccountRepository

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