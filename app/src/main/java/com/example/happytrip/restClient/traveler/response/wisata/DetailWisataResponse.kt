package com.example.happytrip.restClient.traveler.response.wisata

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DetailWisataResponse {
    var success: Boolean? = null
    var message: ArrayList<String>? = null
    var data: Wisata? = null

    class Wisata{
        var id: Int? = null
        var title: String? = null
        var description: String? = null
        var location: String? = null
        @SerializedName("harga_tiket")
        @Expose
        var hargaTiket: Int? = null
        var point: Int? = null
        var denah: String? = null
        var video: String? = null
        var visit: Double? = null
        var rating: Double? = null
        @SerializedName("scan_pointa")
        @Expose
        var scanPoints: ArrayList<ScanPoint>? = null

        class ScanPoint{
            var id: Int? = null
            var title: String? = null
            var description: String? = null
            var point: Int? = null
            var visited: Boolean? = null
        }
    }
}