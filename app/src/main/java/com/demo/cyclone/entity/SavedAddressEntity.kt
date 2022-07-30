package com.demo.cyclone.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_saved_addresses")
data class SavedAddressEntity(
    @PrimaryKey
    val address: String,
    val name: String,
)
