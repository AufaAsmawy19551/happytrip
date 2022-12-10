package com.example.happytrip.restClient.traveler.apiInterface

import com.example.happytrip.restClient.traveler.response.wisata.DetailWisataResponse
import com.example.happytrip.restClient.traveler.response.wisata.ListWisataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface WisataApi {

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @GET("api/traveler/wisatas")
    fun listWisata(): Call<ListWisataResponse>

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @GET("api/traveler/wisatas/{id}")
    fun detailWisata(
        @Path("id") id : Int
    ): Call<DetailWisataResponse>
}