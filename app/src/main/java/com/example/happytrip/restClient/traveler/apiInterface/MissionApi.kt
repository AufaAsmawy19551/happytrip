package com.example.happytrip.restClient.traveler.apiInterface

import com.example.happytrip.restClient.traveler.response.mission.DetailMissionResponse
import com.example.happytrip.restClient.traveler.response.mission.ListMissionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MissionApi {

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @GET("api/traveler/missions")
    fun listMission(
        @Query("title") title: String? = null
    ): Call<ListMissionResponse>

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @GET("api/traveler/missions/{id}")
    fun detailMission(
        @Path("id") id: Int
    ): Call<DetailMissionResponse>
}