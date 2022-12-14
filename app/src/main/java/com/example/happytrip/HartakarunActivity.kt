package com.example.happytrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happytrip.adapter.HartakarunAdapter
import com.example.happytrip.databinding.ActivityHartakarunBinding
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO

class HartakarunActivity : AppCompatActivity() {

    private var _binding : ActivityHartakarunBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHartakarunBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var rvAdapter = TravelerResponseDTO.listHartakarun?.let { HartakarunAdapter(it) }
        Log.e("onCreate, adapter", rvAdapter.toString())

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvHartakarun.setLayoutManager(layoutManager)
        binding.rvHartakarun.setHasFixedSize(true);
        binding.rvHartakarun.adapter = rvAdapter
    }
}