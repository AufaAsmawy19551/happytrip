package com.example.happytrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happytrip.databinding.ActivityDetailMissionBinding
import com.example.happytrip.databinding.ActivityDetailWisataBinding
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO

class DetailWisataActivity : AppCompatActivity() {

    private var _binding : ActivityDetailWisataBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailWisataBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
    }
    fun init(){
        binding.tvTitle.text = TravelerResponseDTO.detailWisata?.toString()
        binding.tvDescription.text = TravelerResponseDTO.detailWisata?.description
        binding.tvHargaTiket.text = TravelerResponseDTO.detailWisata?.hargaTiket.toString()
        binding.tvPoint.text = TravelerResponseDTO.detailWisata?.point.toString()
        binding.tvLocation.text = TravelerResponseDTO.detailWisata?.location
    }
}