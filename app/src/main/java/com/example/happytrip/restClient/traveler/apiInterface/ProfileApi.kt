package com.example.happytrip.restClient.traveler.apiInterface

import com.example.happytrip.restClient.traveler.response.profile.UpdateProfileResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ProfileApi {

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @FormUrlEncoded
    @POST("api/traveler/profile")
    fun profileUpdate(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String,
    ): Call<UpdateProfileResponse>
}