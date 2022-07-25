package com.demo.touchwallet.usecase

import android.content.Context
import com.demo.touchwallet.crypto.Base58Encoder
import com.demo.touchwallet.crypto.Derivation
import com.demo.touchwallet.repository.WalletRepository
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters

object DeriveAccountsUseCase {
    suspend fun deriveAccounts(context: Context): List<String>? {
        val walletRepository = WalletRepository
            .getInstance(context = context)

        val seed = walletRepository.retrieveSeed()

        return if (seed?.seed != null) {
            Derivation.invoke(
                seed = seed.seed
            ).map {
                Base58Encoder.invoke(
                    (it.public as Ed25519PublicKeyParameters).encoded
                )
            }
        } else null
    }
}