package com.demo.cyclone.repository

import android.content.Context
import com.demo.cyclone.database.CycloneDatabase
import com.demo.cyclone.entity.KeyPairEntity
import com.demo.cyclone.entity.SeedEntity
import com.demo.cyclone.extensions.ContextExtensions.touchWalletApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class WalletRepository(context: Context) {
    private var db = CycloneDatabase(context.touchWalletApplication())

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
        private var instance: WalletRepository? = null

        fun getInstance(context: Context): WalletRepository {
            return instance ?: WalletRepository(context).also {
                instance = it
            }
        }
    }
}