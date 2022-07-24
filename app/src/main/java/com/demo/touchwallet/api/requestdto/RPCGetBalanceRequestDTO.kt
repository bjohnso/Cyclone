package com.demo.touchwallet.api.requestdto

data class RPCGetBalanceRequestDTO(
    val method: String = "getBalance",
    val params: List<String>
): RPCRequestDTO()
