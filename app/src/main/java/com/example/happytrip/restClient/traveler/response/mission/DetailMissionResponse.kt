package com.example.happytrip.restClient.traveler.response.mission

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DetailMissionResponse {
    var success: Boolean? = null
    var message: ArrayList<String>? = null
    var data: Mission? = null

    class Mission{
        var id: Int? = null
        var title: String? = null
        var description: String? = null
        var point: Int? = null
        var wisatas: ArrayList<Wisata>? = null

        class Wisata{
            var id: Int? = null
            var title: String? = null
            var description: String? = null
            var point: Int? = null
            @SerializedName("is_visited")
            @Expose
            var isVisited: Boolean? = null
        }
    }
}