package com.example.happytrip.restClient.responseDTO

import com.example.happytrip.restClient.traveler.response.auth.LoginResponse

class TravelerResponseDTO {
    companion object{
        var traveler: LoginResponse.Traveler? = null
        var token: String? = null
    }
}