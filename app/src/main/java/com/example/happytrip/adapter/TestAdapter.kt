package com.example.happytrip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happytrip.databinding.CardHartakarunBinding
import com.example.happytrip.model.Test
import com.example.happytrip.restClient.traveler.response.hartakarun.ListHartakarunResponse

class TestAdapter (
    private var list: List<Test>,
) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardHartakarunBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardHartakarunBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.tvTitle.text = this.title
                binding.tvDescription.text = this.description
                binding.tvPoint.text = this.point.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}