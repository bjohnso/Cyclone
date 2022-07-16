package com.demo.touchwallet.entity

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.touchwallet.usecase.MnemonicEncoder

@Entity(tableName = "tbl_seeds")
data class SeedEntity(
    @PrimaryKey
    @ColumnInfo(
        name = "hex",
        typeAffinity = ColumnInfo.TEXT
    )
    val hex: String,

    @ColumnInfo(
        name = "seed",
        typeAffinity = ColumnInfo.BLOB
    )
    val seed: ByteArray,
) {
    fun getMnemonic(context: Context): List<String> {
        return MnemonicEncoder.invoke(context, seed)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SeedEntity

        if (hex != other.hex) return false
        if (!seed.contentEquals(other.seed)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hex.hashCode()
        result = 31 * result + seed.contentHashCode()
        return result
    }
}
