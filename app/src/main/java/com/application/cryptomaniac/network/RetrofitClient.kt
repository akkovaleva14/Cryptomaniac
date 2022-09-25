package com.application.cryptomaniac.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


class RetrofitClient {
    companion object {
        var api: ApiService? = null
        val json = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }

        @OptIn(ExperimentalSerializationApi::class)
        fun getInstance(): ApiService {
            if (api == null) {
                val contentType = "application/json".toMediaType()

                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client: OkHttpClient =
                    OkHttpClient.Builder().addInterceptor(interceptor).build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.coingecko.com/")
                    .client(client)
                    .addConverterFactory(json.asConverterFactory(contentType))
                    .build()
                api = retrofit.create(ApiService::class.java)
            }
            return api!!
        }
    }
}