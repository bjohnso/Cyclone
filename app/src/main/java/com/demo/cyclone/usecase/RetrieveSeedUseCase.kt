package com.demo.cyclone.usecase

import android.content.Context
import com.demo.cyclone.entity.SeedEntity
import com.demo.cyclone.repository.WalletRepository

object RetrieveSeedUseCase {
    suspend fun retrieveCurrentSeed(context: Context): SeedEntity? {
         return WalletRepository
            .getInstance(context = context)
            .retrieveSeed()
    }
}