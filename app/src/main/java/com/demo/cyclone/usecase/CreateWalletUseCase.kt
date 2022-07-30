package com.demo.cyclone.usecase

import android.content.Context
import com.demo.cyclone.crypto.Base58Encoder
import com.demo.cyclone.crypto.Derivation
import com.demo.cyclone.crypto.MnemonicDecoder
import com.demo.cyclone.entity.KeyPairEntity
import com.demo.cyclone.entity.SeedEntity
import com.demo.cyclone.extensions.ByteExtensions.toHexString
import com.demo.cyclone.extensions.ExceptionExtensions
import com.demo.cyclone.repository.WalletRepository
import com.demo.cyclone.repository.AccountRepository
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import java.security.SecureRandom

object CreateWalletUseCase {
    suspend fun createSeed(context: Context): Boolean {
        return ExceptionExtensions.tryOrDefaultAsync(false) {
            val repository = WalletRepository
                .getInstance(context = context)

            val seed: ByteArray?
            var seedEntity = repository.retrieveSeed()

            val random = SecureRandom()

            if (seedEntity?.seed == null) {
                seed = random.generateSeed(16)
                seedEntity = SeedEntity(
                    hex = seed.toHexString(),
                    seed = seed
                )
            } else random.setSeed(seedEntity.seed)

            repository.destroyAllKeyPairs()
            repository.destroyAllSeeds()

            repository.persistSeed(seedEntity)

            return@tryOrDefaultAsync true
        }
    }

    suspend fun restoreSeed(context: Context, mnemonics: List<String>): Boolean {
        return ExceptionExtensions.tryOrDefaultAsync(false) {
            val repository = WalletRepository
                .getInstance(context = context)

            val seed = MnemonicDecoder.invoke(
                context = context,
                mnemonicList = mnemonics
            )

            if (seed != null) {
                val seedEntity = SeedEntity(
                    hex = seed.toHexString(),
                    seed = seed
                )

                repository.destroyAllKeyPairs()
                repository.destroyAllSeeds()

                repository.persistSeed(seedEntity)
            } else return@tryOrDefaultAsync false

            return@tryOrDefaultAsync true
        }
    }

    suspend fun createKeypair(context: Context): KeyPairEntity? {
        val solanaRepository = WalletRepository
            .getInstance(context = context)

        val userRepository = AccountRepository
            .getInstance()

        val seed = solanaRepository.retrieveSeed()

        return if (seed?.seed != null) {
            val keyPairs = solanaRepository.retrieveAllKeyPairs()
            val derivedKeyPairs = Derivation.invoke(seed = seed.seed)

            derivedKeyPairs.find { derived ->
                val derivedPubKey = Base58Encoder.invoke(
                    (derived.public as Ed25519PublicKeyParameters).encoded
                )

                keyPairs.find {
                    it.publicKey == derivedPubKey
                } == null
            }?.let {
                val keyPair = KeyPairEntity.createFromAsymmetricCipherKeyPair(it)
                solanaRepository.persistKeyPair(keyPair)
                userRepository.setDefaultWallet(
                    context = context,
                    keyPair.publicKey
                )
                keyPair
            }
        } else null
    }
}