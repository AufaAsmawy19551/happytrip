package com.example.happytrip.restClient.traveler.apiInterface

import com.example.happytrip.restClient.traveler.response.scan.ScanResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ScanApi {

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @FormUrlEncoded
    @POST("api/traveler/travelerScan")
    fun scan(
        @Field("code") code: String
    ): Call<ScanResponse>
}