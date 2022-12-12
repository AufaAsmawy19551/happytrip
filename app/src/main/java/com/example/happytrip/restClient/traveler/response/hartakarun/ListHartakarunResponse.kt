package com.example.happytrip.restClient.traveler.response.hartakarun

class ListHartakarunResponse {
    var success: Boolean? = null
    var message: ArrayList<String>? = null
    var data: ArrayList<Hartakarun>? = null

    class Hartakarun{
        var id: Int? = null
        var title: String? = null
        var description: String? = null
        var point: Int? = null
        var redeemed: Boolean? = null
    }
}