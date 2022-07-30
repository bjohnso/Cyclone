package com.demo.cyclone.dao

import androidx.room.*
import com.demo.cyclone.entity.SavedAddressEntity

interface SavedAddressDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun persistSavedAddress(vararg savedAddress: SavedAddressEntity)

    @Transaction
    @Query("select * from tbl_token_transfers")
    fun retrieveAllSavedAddresses(): List<SavedAddressEntity?>

    @Transaction
    @Query("select * from tbl_saved_addresses where address = :address limit 1")
    fun retrieveSavedAddress(address: String): SavedAddressEntity?

    @Transaction
    @Delete
    fun destroyTokenTransfers(vararg savedAddress: SavedAddressEntity)

    @Transaction
    @Query("delete from tbl_saved_addresses")
    fun destroyAllTokenTransfers()
}