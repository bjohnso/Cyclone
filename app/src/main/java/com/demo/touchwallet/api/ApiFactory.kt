package com.demo.touchwallet.api

import android.content.Context
import com.demo.touchwallet.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {
    private val apiClient: ApiInterface? = null

    private const val TIMEOUT_CONNECT = 20
    private const val TIMEOUT_READ = 40

    fun getApiClient(context: Context): ApiInterface {
        return apiClient ?: Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.devnet))
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}