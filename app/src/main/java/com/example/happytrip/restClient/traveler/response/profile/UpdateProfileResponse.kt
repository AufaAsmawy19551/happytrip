package com.example.happytrip.restClient.traveler.response.profile

import com.example.happytrip.restClient.traveler.response.auth.LoginResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UpdateProfileResponse {
    var success: Boolean? = null
    var message: List<String>? = null
    @SerializedName("data")
    @Expose
    var user: LoginResponse.Traveler? = null
}
