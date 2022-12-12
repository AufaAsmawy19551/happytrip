package com.example.happytrip.restClient.responseDTO

import com.example.happytrip.restClient.traveler.response.auth.LoginResponse
import com.example.happytrip.restClient.traveler.response.hartakarun.ListHartakarunResponse
import com.example.happytrip.restClient.traveler.response.mission.ListMissionResponse

class TravelerResponseDTO {
    companion object{
        var token: String? = null
        var traveler: LoginResponse.Traveler? = null
        var hartakaruns: List<ListHartakarunResponse.Hartakarun>? = null
        var missions: List<ListMissionResponse.Mission>? = null
    }
}