package com.demo.touchwallet.api

import com.demo.touchwallet.api.requestdto.RPCGetBalanceRequestDTO
import com.demo.touchwallet.api.responsedto.RPCGetBalanceResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("/")
    fun getBalance(
        @Body body: RPCGetBalanceRequestDTO
    ): Call<RPCGetBalanceResponseDTO>
}