package com.demo.touchwallet.api.requestdto

open class RPCRequestDTO(
    open val jsonrpc: String = "2.0",
    open val id: Int = 1,
)