package com.demo.cyclone.api.responsedto

data class RPCGetBalanceResponseDTO(
    val jsonrpc: String,
    val id: Int,
    val result: Result
) {
    data class Result(
        val context: Context,
        val value: Float
    )

    data class Context(
        val apiVersion: String,
        val slot: Int
    )
}