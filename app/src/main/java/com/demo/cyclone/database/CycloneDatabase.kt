package com.demo.cyclone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.demo.cyclone.dao.KeyPairDao
import com.demo.cyclone.dao.SeedDao
import com.demo.cyclone.entity.KeyPairEntity
import com.demo.cyclone.entity.SavedAddressEntity
import com.demo.cyclone.entity.SeedEntity
import com.demo.cyclone.entity.TokenTransferEntity
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteDatabaseHook
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [
            KeyPairEntity::class,
            SeedEntity::class,
            SavedAddressEntity::class,
            TokenTransferEntity::class
        ],
    version = 1
)
abstract class CycloneDatabase: RoomDatabase() {
    abstract fun keyPairDao(): KeyPairDao
    abstract fun seedDao(): SeedDao

    companion object {
        @Volatile private var instance: CycloneDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context = context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context): CycloneDatabase {
            val passphrase: ByteArray = SQLiteDatabase.getBytes("password".toCharArray())
            val factory = SupportFactory(passphrase, object: SQLiteDatabaseHook {
                override fun preKey(database: SQLiteDatabase?) {}

                override fun postKey(database: SQLiteDatabase?) {}
            }, false)

            return databaseBuilder(context, CycloneDatabase::class.java, "solana.db")
//                .openHelperFactory(factory)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}