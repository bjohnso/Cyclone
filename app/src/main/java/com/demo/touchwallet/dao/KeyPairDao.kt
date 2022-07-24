package com.demo.touchwallet.dao

import androidx.room.*
import com.demo.touchwallet.entity.KeyPairEntity

@Dao
interface KeyPairDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun persistKeyPairs(vararg keyPair: KeyPairEntity)

    @Transaction
    @Query("select * from tbl_keypairs where public_key = :publicKey limit 1")
    fun retrieveKeyPair(publicKey: String): KeyPairEntity?

    @Transaction
    @Query("select * from tbl_keypairs")
    fun retrieveAllKeyPairs(): List<KeyPairEntity>

    @Transaction
    @Delete
    fun destroyKeyPairs(vararg keyPair: KeyPairEntity)

    @Transaction
    @Query("delete from tbl_keypairs")
    fun destroyAllKeyPairs()
}