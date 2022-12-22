package com.example.happytrip.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.happytrip.R
import com.example.happytrip.databinding.FragmentFirstBinding
import com.example.happytrip.restClient.traveler.response.wisata.ListWisataResponse
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.gson.Gson

class WisataAdapter(context: Context): GoogleMap.InfoWindowAdapter {

    var view = LayoutInflater.from(context).inflate(R.layout.card_wisata, null)
    private var gson = Gson()

    private fun setInfoWindowText(marker: Marker, wisata: ListWisataResponse.Wisata) {
        view.findViewById<ImageView>(R.id.ivImage).setVisibility(View.VISIBLE)
        view.findViewById<TextView>(R.id.tvHargaTiket).setVisibility(View.VISIBLE)
        view.findViewById<ProgressBar>(R.id.pbProgress).setVisibility(View.VISIBLE)
        view.findViewById<TextView>(R.id.tvProgress).setVisibility(View.VISIBLE)
        view.findViewById<TextView>(R.id.tvRating).setVisibility(View.VISIBLE)
        view.findViewById<TextView>(R.id.tvVisit).setVisibility(View.VISIBLE)
        Glide.with(view.findViewById<ImageView>(R.id.ivImage).context).load(wisata.image).into(view.findViewById<ImageView>(R.id.ivImage))
        view.findViewById<TextView>(R.id.tvTitle).text = marker.title
        view.findViewById<TextView>(R.id.tvHargaTiket).text = "Harga Tiket: Rp" + wisata.hargaTiket.toString()
        view.findViewById<ProgressBar>(R.id.pbProgress).max = wisata.scanPoint!!
        view.findViewById<ProgressBar>(R.id.pbProgress).progress = wisata.scanned!!
        view.findViewById<TextView>(R.id.tvProgress).text = "${wisata.scanned}/${wisata.scanPoint}"
        view.findViewById<TextView>(R.id.tvRating).text = wisata.rating.toString()
        view.findViewById<TextView>(R.id.tvVisit).text = wisata.visit.toString()

    }

    override fun getInfoWindow(marker: Marker): View {
        if(marker.title.equals("Lokasi Kamu")){
            view.findViewById<TextView>(R.id.tvTitle).text = marker.title
            view.findViewById<ImageView>(R.id.ivImage).setVisibility(View.GONE)
            view.findViewById<TextView>(R.id.tvHargaTiket).setVisibility(View.GONE)
            view.findViewById<ProgressBar>(R.id.pbProgress).setVisibility(View.GONE)
            view.findViewById<TextView>(R.id.tvProgress).setVisibility(View.GONE)
            view.findViewById<TextView>(R.id.tvRating).setVisibility(View.GONE)
            view.findViewById<TextView>(R.id.tvVisit).setVisibility(View.GONE)
        }else{
            setInfoWindowText(marker, gson.fromJson(marker.snippet, ListWisataResponse.Wisata::class.java)!!)
        }
        return view
    }

    override fun getInfoContents(marker: Marker): View {
        if(marker.title.equals("Lokasi Kamu")){
            view.findViewById<TextView>(R.id.tvTitle).text = marker.title
            view.findViewById<ImageView>(R.id.ivImage).setVisibility(View.GONE)
            view.findViewById<TextView>(R.id.tvHargaTiket).setVisibility(View.GONE)
            view.findViewById<ProgressBar>(R.id.pbProgress).setVisibility(View.GONE)
            view.findViewById<TextView>(R.id.tvProgress).setVisibility(View.GONE)
            view.findViewById<TextView>(R.id.tvRating).setVisibility(View.GONE)
            view.findViewById<TextView>(R.id.tvVisit).setVisibility(View.GONE)
        }else{
            setInfoWindowText(marker, gson.fromJson(marker.snippet, ListWisataResponse.Wisata::class.java)!!)
        }
        return view
    }
}