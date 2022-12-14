package com.example.happytrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happytrip.databinding.ActivityDetailMissionBinding
import com.example.happytrip.databinding.ActivityHartakarunBinding
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO

class DetailMissionActivity : AppCompatActivity() {

    private var _binding : ActivityDetailMissionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailMissionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
    }
    fun init(){
        binding.tvTitle.text = TravelerResponseDTO.detailMission?.title
        binding.tvDescription.text = TravelerResponseDTO.detailMission?.description
        binding.tvPoint.text = TravelerResponseDTO.detailMission?.point.toString()
    }
}