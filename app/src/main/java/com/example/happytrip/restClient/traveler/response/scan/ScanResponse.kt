package com.example.happytrip.restClient.traveler.response.scan

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ScanResponse {
    var success: Boolean? = null
    var message: ArrayList<String>? = null
    var data: ScanPoint? = null

    class ScanPoint{
        var id: Int? = null
        @SerializedName("wisata_id")
        @Expose
        var wisataId: Int? = null
        var title: String? = null
        var description: String? = null
        var point: Int? = null
    }
}