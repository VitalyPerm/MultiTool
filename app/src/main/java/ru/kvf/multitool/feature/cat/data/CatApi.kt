package ru.kvf.multitool.feature.cat.data

import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("images/search")
    suspend fun getCats(@Query("limit") count: Int): List<RandomCatResponse>
}