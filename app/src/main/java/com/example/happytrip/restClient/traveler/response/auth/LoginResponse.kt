package com.example.happytrip.restClient.traveler.response.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    var success: Boolean? = null
    var message: List<String>? = null
    var user: Traveler? = null
    var token: String? = null

    class Traveler {
        var id: Int? = null
        @SerializedName("badge_id")
        @Expose
        var badgeId: Int? = null
        var name: String? = null
        var slug: String? = null
        var email: String? = null
        var image: String? = null
        @SerializedName("current_point")
        @Expose
        var currentPoint: Int? = null
        @SerializedName("collected_point")
        @Expose
        var collectedPoint: Int? = null
    }
}