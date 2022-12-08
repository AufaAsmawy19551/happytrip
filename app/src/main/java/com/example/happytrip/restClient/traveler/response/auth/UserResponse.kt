package com.example.happytrip.restClient.traveler.response.auth

class UserResponse {
    var success: Boolean? = null
    var message: List<String>? = null
    var user: LoginResponse.Traveler? = null
}