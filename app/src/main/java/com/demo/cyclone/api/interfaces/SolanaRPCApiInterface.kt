package com.demo.cyclone.api.interfaces

import com.demo.cyclone.api.requestdto.RPCGetBalanceRequestDTO
import com.demo.cyclone.api.responsedto.RPCGetBalanceResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SolanaRPCApiInterface {
    @POST("/")
    fun getBalance(
        @Body body: RPCGetBalanceRequestDTO
    ): Call<RPCGetBalanceResponseDTO?>
}