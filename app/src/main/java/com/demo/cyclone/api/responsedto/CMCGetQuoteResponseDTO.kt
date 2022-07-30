package com.demo.cyclone.api.responsedto

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CMCGetQuoteResponseDTO(
    val status: Status,
    val data: JsonObject,
) {
    data class Status(
        val timestamp: String,
        @SerializedName("error_code")
        val errorCode: Int,
        @SerializedName("error_message")
        val errorMessage: String?,
        val elapsed: Int,
        @SerializedName("credit_count")
        val creditCount: Int,
    )

    val symbols get() = data.keySet().toList()

    fun getQuoteUSD(symbol: String, slug: String? = null): Float? {
        val quote = data[symbol] as? JsonArray

        return when(slug) {
            null -> quote?.find {
                it.asJsonObject["symbol"].asString == symbol
            }
            else -> quote?.find {
                it.asJsonObject["symbol"].asString == symbol &&
                        it.asJsonObject["slug"].asString == slug
            }
        }?.asJsonObject?.get("quote")
            ?.asJsonObject?.get("USD")
            ?.asJsonObject?.get("price")
            ?.asFloat
    }
}