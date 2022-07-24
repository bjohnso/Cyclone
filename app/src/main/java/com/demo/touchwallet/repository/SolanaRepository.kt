package com.demo.touchwallet.repository

import android.content.Context
import com.demo.touchwallet.database.SolanaDatabase
import com.demo.touchwallet.entity.KeyPairEntity
import com.demo.touchwallet.entity.SeedEntity
import com.demo.touchwallet.extensions.ByteExtensions.toHexString
import com.demo.touchwallet.extensions.ContextExtensions.touchWalletApplication
import com.demo.touchwallet.extensions.ExceptionExtensions
import com.demo.touchwallet.crypto.Derivation
import com.demo.touchwallet.crypto.MnemonicDecoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import java.security.SecureRandom

class SolanaRepository(context: Context) {
    private var db: SolanaDatabase = SolanaDatabase(context.touchWalletApplication())

    fun flowOnSeed(): Flow<SeedEntity?> = db.seedDao().flowOnSeed()

    suspend fun retrieveSeed() =
        withContext(Dispatchers.IO) {
            return@withContext db.seedDao().retrieveSeed()
        }

    suspend fun persistSeed(vararg seed: SeedEntity) =
        withContext(Dispatchers.IO) {
            db.seedDao().persistSeeds(*seed)
        }

    suspend fun destroyAllSeeds() =
        withContext(Dispatchers.IO) {
            db.seedDao().destroyAllSeeds()
        }

    suspend fun retrieveKeyPair(address: String) =
        withContext(Dispatchers.IO) {
            db.keyPairDao().retrieveKeyPair(address)
        }

    suspend fun retrieveAllKeyPairs() =
        withContext(Dispatchers.IO) {
            db.keyPairDao().retrieveAllKeyPairs()
        }

    suspend fun persistKeyPair(vararg keyPair: KeyPairEntity) =
        withContext(Dispatchers.IO) {
            db.keyPairDao().persistKeyPairs(*keyPair)
        }

    suspend fun destroyAllKeyPairs() =
        withContext(Dispatchers.IO) {
            db.keyPairDao().destroyAllKeyPairs()
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