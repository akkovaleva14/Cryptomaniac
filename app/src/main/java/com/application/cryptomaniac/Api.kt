package com.application.cryptomaniac

import com.application.cryptomaniac.data.model.Crypto
import com.application.cryptomaniac.data.model.CryptoDescAndCat
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("/coins/markets?")
    suspend fun getUsdOrEur(
        @Query("vs_currency") vs_currency: String = "usd", // or "eur"
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") per_page: Int = 20,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false,
    ): List<String>

    @GET("/coins/{id}")
    suspend fun getDescAndCat(
        @Path(value = "id") id : String
    ): List<String>


    companion object {
        var api: Api? = null
        @OptIn(ExperimentalSerializationApi::class)
        fun getInstance(): Api {
            if (api == null) {
                val contentType = "application/json".toMediaType()
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.coingecko.com/api/v3/")
                    .addConverterFactory(Json.asConverterFactory(contentType))
                    // .addConverterFactory(Json(JsonConfiguration(strictMode = false)).asConverterFactory(contentType))
                    .build()
                api = retrofit.create(Api::class.java)
            }
            return api!!
        }

    }
}

/*
* kotlinx serialization https://medium.com/@jurajkunier/kotlinx-json-vs-gson-4ba24a21bd73
* */