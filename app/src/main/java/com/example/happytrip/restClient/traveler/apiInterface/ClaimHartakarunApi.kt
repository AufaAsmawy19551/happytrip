package com.example.happytrip.restClient.traveler.apiInterface

import com.example.happytrip.restClient.traveler.response.claimHartakarun.ClaimHartakarunResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ClaimHartakarunApi {

//    @Headers(value = [
//        "Accept: application/json",
//        "Content-type:application/json"
//    ])
    @FormUrlEncoded
    @POST("api/traveler/travelerRedeem")
    fun claimHartakarun(
        @Field("hartakarun_id") hartakarunId: Int
    ): Call<ClaimHartakarunResponse>
}