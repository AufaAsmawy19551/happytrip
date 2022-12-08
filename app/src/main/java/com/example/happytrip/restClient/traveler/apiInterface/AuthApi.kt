package com.example.happytrip.restClient.traveler.apiInterface

import com.example.happytrip.restClient.traveler.response.auth.*
import retrofit2.Call
import retrofit2.http.*

interface AuthApi {

    @FormUrlEncoded
    @POST("api/traveler/login")
    fun authLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @GET("api/traveler/user")
    fun authUser(): Call<UserResponse>

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @GET("api/traveler/refresh")
    fun authRefreshToken(): Call<RefreshTokenResponse>

    @Headers(value = [
        "Accept: application/json",
        "Content-type:application/json"
    ])
    @POST("api/traveler/logout")
    fun authLogout(): Call<LogoutResponse>
}