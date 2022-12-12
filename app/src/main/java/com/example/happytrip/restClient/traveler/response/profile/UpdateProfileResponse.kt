package com.example.happytrip.restClient.traveler.response.profile

import com.example.happytrip.restClient.traveler.response.auth.LoginResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class UpdateProfileResponse {
    var success: Boolean? = null
    var message: ArrayList<String>? = null
    @SerializedName("data")
    @Expose
    var user: LoginResponse.Traveler? = null
}
