package com.example.happytrip.restClient.traveler.apiInterface

import com.example.happytrip.restClient.traveler.response.badge.DetailBadgeResponse
import com.example.happytrip.restClient.traveler.response.badge.ListBadgeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface BadgeApi {

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @GET("api/traveler/badges")
    fun listBadge(): Call<ListBadgeResponse>

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @GET("api/traveler/badges/{id}")
    fun detailBadge(
        @Path("id") id : Int
    ): Call<DetailBadgeResponse>
}