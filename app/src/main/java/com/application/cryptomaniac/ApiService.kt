package com.application.cryptomaniac

import com.application.cryptomaniac.data.model.Crypto
import com.application.cryptomaniac.data.model.CryptoDescAndCategories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/api/v3/coins/markets")
    suspend fun getUsdOrEur(
        @Query("vs_currency") vs_currency: String,
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") per_page: Int = 20,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false
    ): Response<List<Crypto>>

    @GET("/api/v3/coins/{id}")
    suspend fun getDescAndCategories(
        @Path(value = "id") id: String
    ): Response<CryptoDescAndCategories>
}
