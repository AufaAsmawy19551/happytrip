package com.example.happytrip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happytrip.databinding.CardMissionBinding
import com.example.happytrip.restClient.traveler.response.mission.ListMissionResponse

class MissionAdapter (
    private var list: List<ListMissionResponse.Mission>,
) : RecyclerView.Adapter<MissionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardMissionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.tvTitle.text = this.title
                binding.tvPoint.text = this.point.toString()
                binding.tvProgress.text = "${this.visitedWisata.toString()}/${this.wisata.toString()}"
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}