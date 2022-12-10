package com.example.happytrip.restClient.responseDTO

import com.example.happytrip.restClient.traveler.response.auth.LoginResponse
import com.example.happytrip.restClient.traveler.response.hartakarun.ListHartakarunResponse

class TravelerResponseDTO {
    companion object{
        var token: String? = null
        var traveler: LoginResponse.Traveler? = null
        lateinit var hartakaruns: List<ListHartakarunResponse.Hartakarun>
    }
}