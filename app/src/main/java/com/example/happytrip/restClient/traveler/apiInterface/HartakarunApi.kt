package com.example.happytrip.restClient.traveler.apiInterface

import com.example.happytrip.restClient.traveler.response.hartakarun.DetailHartakarunResponse
import com.example.happytrip.restClient.traveler.response.hartakarun.ListHartakarunResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface HartakarunApi {

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @GET("api/traveler/hartakaruns")
    fun listHartakarun(
        @Query("title") title: String? = null
    ): Call<ListHartakarunResponse>

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @GET("api/traveler/hartakaruns/{id}")
    fun detailHartakarun(
        @Path("id") title: Int
    ): Call<DetailHartakarunResponse>
}