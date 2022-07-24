package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.crypto.Derivation
import com.demo.touchwallet.entity.KeyPairEntity
import com.demo.touchwallet.repository.SolanaRepository

object DeriveAccountsUseCase {
    suspend fun deriveAccounts(context: Context): List<KeyPairEntity>? {
        val repository = SolanaRepository
            .getInstance(context = context)

        val seed = repository.retrieveSeed()

        return if (seed?.seed != null) {
            Derivation.invoke(
                seed = seed.seed
            ).map {
                KeyPairEntity.createFromAsymmetricCipherKeyPair(it)
            }
        } else null
    }
}