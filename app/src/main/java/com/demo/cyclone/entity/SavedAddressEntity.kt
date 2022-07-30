package com.demo.cyclone.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_saved_addresses")
data class SavedAddressEntity(
    @PrimaryKey
    @ColumnInfo(
        name = "address",
        typeAffinity = ColumnInfo.TEXT
    )
    val address: String,
    val name: String,
)
