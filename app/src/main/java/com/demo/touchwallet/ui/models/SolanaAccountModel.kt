package com.demo.touchwallet.ui.models

data class SolanaAccountModel(
    val publicKey: String,
    val lamports: Float
    ) {

    val solanaBalance get() = lamports / LAMPORTS_PER_SOL

    companion object {
        const val LAMPORTS_PER_SOL = 1000000000
    }
}
