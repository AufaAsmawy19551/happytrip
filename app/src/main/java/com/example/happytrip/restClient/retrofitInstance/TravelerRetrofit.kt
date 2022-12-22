package com.example.happytrip.restClient.retrofitInstance

import com.example.happytrip.config.Config
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TravelerRetrofit {
    var client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${TravelerResponseDTO.token.toString()}")
            .build()
        chain.proceed(newRequest)
    }.build()

    fun getRetroClientInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Config.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}