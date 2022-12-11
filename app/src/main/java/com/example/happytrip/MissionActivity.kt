package com.example.happytrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happytrip.adapter.MissionAdapter
import com.example.happytrip.databinding.ActivityMissionBinding
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO

class MissionActivity : AppCompatActivity() {

    private var _binding : ActivityMissionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMissionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var rvAdapter = TravelerResponseDTO.missions?.let { MissionAdapter(it) }

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvMission.setLayoutManager(layoutManager)
        binding.rvMission.setHasFixedSize(true);
        binding.rvMission.adapter = rvAdapter
    }
}