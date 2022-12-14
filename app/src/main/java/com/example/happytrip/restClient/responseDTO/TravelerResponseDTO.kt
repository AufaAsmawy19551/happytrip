package com.example.happytrip.restClient.responseDTO

import com.example.happytrip.restClient.traveler.response.auth.LoginResponse
import com.example.happytrip.restClient.traveler.response.hartakarun.ListHartakarunResponse
import com.example.happytrip.restClient.traveler.response.mission.DetailMissionResponse
import com.example.happytrip.restClient.traveler.response.mission.ListMissionResponse
import com.example.happytrip.restClient.traveler.response.wisata.DetailWisataResponse
import com.example.happytrip.restClient.traveler.response.wisata.ListWisataResponse

class TravelerResponseDTO {
    companion object{
        var token: String? = null
        var traveler: LoginResponse.Traveler? = null
        var listHartakarun: List<ListHartakarunResponse.Hartakarun>? = null
        var listMission: List<ListMissionResponse.Mission>? = null
        var listWisata: List<ListWisataResponse.Wisata>? = null
        var detailHartakarun: ListHartakarunResponse.Hartakarun? = null
        var detailMission: DetailMissionResponse.Mission? = null
        var detailWisata: DetailWisataResponse.Wisata? = null
    }
}