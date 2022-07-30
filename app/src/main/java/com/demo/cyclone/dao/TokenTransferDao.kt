package com.demo.cyclone.dao

import androidx.room.*
import com.demo.cyclone.entity.TokenTransferEntity

@Dao
interface TokenTransferDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun persistTokenTransfer(vararg tokenTransfer: TokenTransferEntity)

    @Transaction
    @Query("select * from tbl_token_transfers order by createdAt desc limit 1")
    fun retrieveLatestTokenTransfer(): TokenTransferEntity?

    @Transaction
    @Query("select * from tbl_token_transfers where recipient = :recipient")
    fun retrieveTokenTransferByRecipient(recipient: String): List<TokenTransferEntity?>

    @Transaction
    @Query("select * from tbl_token_transfers where sender = :sender")
    fun retrieveTokenTransferBySender(sender: String): List<TokenTransferEntity?>

    @Transaction
    @Delete
    fun destroyTokenTransfers(vararg tokenTransfer: TokenTransferEntity)

    @Transaction
    @Query("delete from tbl_token_transfers")
    fun destroyAllTokenTransfers()
}