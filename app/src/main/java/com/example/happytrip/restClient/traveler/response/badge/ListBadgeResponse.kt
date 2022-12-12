package com.example.happytrip.restClient.traveler.response.badge

import java.util.ArrayList

class ListBadgeResponse {
    var success: Boolean? = null
    var message: ArrayList<String>? = null
    var data: ArrayList<Badge>? = null

    class Badge{
        var id: Int? = null
        var title: String? = null
        var image: String? = null
        var point: Int? = null
    }
}