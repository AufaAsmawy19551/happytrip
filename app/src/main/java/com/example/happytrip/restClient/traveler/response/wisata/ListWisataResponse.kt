package com.example.happytrip.restClient.traveler.response.wisata

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class ListWisataResponse {
    var success: Boolean? = null
    var message: ArrayList<String>? = null
    var data: ArrayList<Wisata>? = null

    class Wisata{
        var id: Int? = null
        var title: String? = null
        var location: String? = null
        @SerializedName("is_visited")
        @Expose
        var isVisited: Boolean? = null
    }
}