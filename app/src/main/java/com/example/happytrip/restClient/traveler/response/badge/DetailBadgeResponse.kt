package com.example.happytrip.restClient.traveler.response.badge

import java.util.ArrayList

class DetailBadgeResponse {
    var success: Boolean? = null
    var message: ArrayList<String>? = null
    var data: Badge? = null

    class Badge{
        var id: Int? = null
        var title: String? = null
        var image: String? = null
        var point: Int? = null
    }
}