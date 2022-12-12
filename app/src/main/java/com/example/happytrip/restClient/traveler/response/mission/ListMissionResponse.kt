package com.example.happytrip.restClient.traveler.response.mission

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListMissionResponse {
    var success: Boolean? = null
    var message: ArrayList<String>? = null
    var data: ArrayList<Mission>? = null

    class Mission{
        var id: Int? = null
        var title: String? = null
        var point: Int? = null
        var wisata: Int? = null
        @SerializedName("visited_wisata")
        @Expose
        var visitedWisata: Int? = null
    }
}