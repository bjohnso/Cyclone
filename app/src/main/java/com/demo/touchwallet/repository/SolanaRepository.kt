package com.demo.touchwallet.repository

import android.content.Context
import com.demo.touchwallet.database.SolanaDatabase
import com.demo.touchwallet.entity.KeyPairEntity
import com.demo.touchwallet.entity.SeedEntity
import com.demo.touchwallet.extensions.ByteExtensions.toHexString
import com.demo.touchwallet.extensions.ContextExtensions.touchWalletApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters
import java.security.SecureRandom

class SolanaRepository(context: Context) {
    private var db: SolanaDatabase = SolanaDatabase(context.touchWalletApplication())

    suspend fun generateKeyPair(): Boolean {
        val seed: ByteArray?
        var seedEntity = retrieveSeed()

        val random = SecureRandom()

        if (seedEntity?.seed == null) {
            seed = random.generateSeed(16)
            seedEntity = SeedEntity(
                hex = seed.toHexString(),
                seed = seed
            )
        } else random.setSeed(seedEntity.seed)

        val keyPairGenerator = Ed25519KeyPairGenerator().apply {
            init(Ed25519KeyGenerationParameters(random))
        }

        val keyPairEntity = KeyPairEntity.createFromAsymmetricCipherKeyPair(
            keyPairGenerator.generateKeyPair()
        )

        persistSeed(seedEntity)
        persistKeyPair(keyPairEntity)

        return true
    }

    suspend fun retrieveSeed() =
        withContext(Dispatchers.IO) {
            return@withContext db.seedDao().retrieveSeed()
        }

    fun flowOnSeed(): Flow<SeedEntity?> = db.seedDao().flowOnSeed()

    suspend fun persistSeed(vararg seed: SeedEntity) =
        withContext(Dispatchers.IO) {
            db.seedDao().persistSeeds(*seed)
        }

    suspend fun persistKeyPair(vararg keyPair: KeyPairEntity) =
        withContext(Dispatchers.IO) {
            db.keyPairDao().persistKeyPairs(*keyPair)
        }

    companion object {
        private var instance: SolanaRepository? = null

        fun getInstance(context: Context): SolanaRepository {
            return instance ?: SolanaRepository(context).also {
                instance = it
            }
        }
    }
}