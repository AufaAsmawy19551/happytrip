package com.example.happytrip.restClient.traveler.response.claimHartakarun

import com.example.happytrip.restClient.traveler.response.hartakarun.ListHartakarunResponse

class ClaimHartakarunResponse {
    var success: Boolean? = null
    var message: ArrayList<String>? = null
    var data: ArrayList<ListHartakarunResponse.Hartakarun>? = null
}