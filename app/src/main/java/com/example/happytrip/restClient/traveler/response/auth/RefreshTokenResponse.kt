package com.example.happytrip.restClient.traveler.response.auth

class RefreshTokenResponse {
    var success: Boolean? = null
    var message: ArrayList<String>? = null
    var user: LoginResponse.Traveler? = null
    var token: String? = null
}