package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.entity.KeyPairEntity

object RetrieveOrCreateWalletUseCase {
    suspend fun retrieveOrCreateDefaultWallet(context: Context): KeyPairEntity? {
        return RetrieveWalletUseCase.retrieveCurrentWallet(
            context = context
        ) ?: CreateWalletUseCase.createKeypair(
            context = context
        )
    }
}