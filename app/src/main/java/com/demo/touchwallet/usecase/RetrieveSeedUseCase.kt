package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.entity.SeedEntity
import com.demo.touchwallet.repository.WalletRepository

object RetrieveSeedUseCase {
    suspend fun retrieveCurrentSeed(context: Context): SeedEntity? {
         return WalletRepository
            .getInstance(context = context)
            .retrieveSeed()
    }
}