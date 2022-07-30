package com.demo.cyclone.api.requestdto

data class RPCGetBalanceRequestDTO(
    val method: String = "getBalance",
    val params: List<String>
): RPCRequestDTO()
