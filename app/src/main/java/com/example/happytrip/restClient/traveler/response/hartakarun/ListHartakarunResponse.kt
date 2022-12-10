package com.example.happytrip.restClient.traveler.response.hartakarun

import com.example.happytrip.restClient.traveler.response.auth.LoginResponse

class ListHartakarunResponse {
    var success: Boolean? = null
    var message: List<String>? = null
    var data: List<Hartakarun>? = null

    class Hartakarun{
        var id: Int? = null
        var title: String? = null
        var description: String? = null
        var point: Int? = null
        var redeemed: Boolean? = false
    }
}