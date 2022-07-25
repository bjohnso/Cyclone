package com.demo.touchwallet.api.interfaces

import com.demo.touchwallet.api.requestdto.RPCGetBalanceRequestDTO
import com.demo.touchwallet.api.responsedto.RPCGetBalanceResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SolanaRPCApiInterface {
    @POST("/")
    fun getBalance(
        @Body body: RPCGetBalanceRequestDTO
    ): Call<RPCGetBalanceResponseDTO?>
}