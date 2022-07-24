package com.demo.touchwallet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.demo.touchwallet.dao.KeyPairDao
import com.demo.touchwallet.dao.SeedDao
import com.demo.touchwallet.entity.KeyPairEntity
import com.demo.touchwallet.entity.SeedEntity
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteDatabaseHook
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [KeyPairEntity::class, SeedEntity::class],
    version = 1
)
abstract class SolanaDatabase: RoomDatabase() {
    abstract fun keyPairDao(): KeyPairDao
    abstract fun seedDao(): SeedDao

    companion object {
        @Volatile private var instance: SolanaDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context = context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context): SolanaDatabase {
            val passphrase: ByteArray = SQLiteDatabase.getBytes("password".toCharArray())
            val factory = SupportFactory(passphrase, object: SQLiteDatabaseHook {
                override fun preKey(database: SQLiteDatabase?) {}

                override fun postKey(database: SQLiteDatabase?) {}
            }, false)

            return databaseBuilder(context, SolanaDatabase::class.java, "solana.db")
//                .openHelperFactory(factory)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}