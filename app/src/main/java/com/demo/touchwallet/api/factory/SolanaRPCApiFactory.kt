package com.demo.touchwallet.api.factory

import android.content.Context
import com.demo.touchwallet.R
import com.demo.touchwallet.api.interfaces.SolanaRPCApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object SolanaRPCApiFactory {
    private val API_CLIENT: SolanaRPCApiInterface? = null

    private const val TIMEOUT_CONNECT = 20
    private const val TIMEOUT_READ = 40

    fun getApiClient(context: Context): SolanaRPCApiInterface {
        return API_CLIENT ?: Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.url_devnet))
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SolanaRPCApiInterface::class.java)
    }
}