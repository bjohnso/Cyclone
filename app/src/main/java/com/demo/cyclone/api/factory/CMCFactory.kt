package com.demo.cyclone.api.factory

import android.content.Context
import com.demo.cyclone.BuildConfig
import com.demo.cyclone.R
import com.demo.cyclone.api.interfaces.CoinMarketCapInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CMCFactory {
    private val API_CLIENT: CoinMarketCapInterface? = null

    private const val X_CMC_PRO_API_KEY_HEADER = "X-CMC_PRO_API_KEY"

    private const val TIMEOUT_CONNECT = 20
    private const val TIMEOUT_READ = 40

    private fun getHeaderInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader(X_CMC_PRO_API_KEY_HEADER, BuildConfig.CMC_API_KEY)

            chain.proceed(requestBuilder.build())
        }
    }

    fun getApiClient(context: Context): CoinMarketCapInterface {
        return API_CLIENT ?: Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.url_cmc))
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
                    .addInterceptor(getHeaderInterceptor())
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinMarketCapInterface::class.java)
    }
}