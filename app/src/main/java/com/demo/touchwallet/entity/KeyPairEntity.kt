package com.demo.touchwallet.entity

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.TEXT
import androidx.room.ColumnInfo.BLOB
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.touchwallet.usecase.Base58Encoder
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters

@Entity(tableName = "tbl_keypairs")
data class KeyPairEntity(
    @PrimaryKey
    @ColumnInfo(
        name = "public_key",
        typeAffinity = TEXT
    )
    val publicKey: String,

    @ColumnInfo(
        name = "private_key",
        typeAffinity = BLOB
    )
    val privateKey: ByteArray,
) {

    init {
        require(privateKey.size == PRIVATE_KEY_SIZE) {
            "Invalid private key length"
        }
    }

    companion object {
        const val PRIVATE_KEY_SIZE = 32

        fun createFromAsymmetricCipherKeyPair(keyPair: AsymmetricCipherKeyPair): KeyPairEntity {
            val publicKey = (keyPair.public as Ed25519PublicKeyParameters).encoded
            val privateKey = (keyPair.private as Ed25519PrivateKeyParameters).encoded

            return KeyPairEntity(
                publicKey = Base58Encoder.invoke(publicKey),
                privateKey = privateKey
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KeyPairEntity

        if (publicKey != other.publicKey) return false
        if (!privateKey.contentEquals(other.privateKey)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = publicKey.hashCode()
        result = 31 * result + privateKey.contentHashCode()
        return result
    }
}