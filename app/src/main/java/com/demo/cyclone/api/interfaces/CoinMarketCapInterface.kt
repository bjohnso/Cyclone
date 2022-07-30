package com.demo.cyclone.api.interfaces

import com.demo.cyclone.api.responsedto.CMCGetQuoteResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinMarketCapInterface {
    @GET("/v2/cryptocurrency/quotes/latest")
    fun getQuote(
        @Query("symbol") symbol: String
    ): Call<CMCGetQuoteResponseDTO?>
}