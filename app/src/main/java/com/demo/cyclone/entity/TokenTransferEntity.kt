package com.demo.cyclone.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_token_transfers")
data class TokenTransferEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val recipient: String,
    val sender: String,
    val timestamp: Long
)
