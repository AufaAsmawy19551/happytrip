package com.example.happytrip.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happytrip.databinding.CardHartakarunBinding
import com.example.happytrip.restClient.traveler.response.hartakarun.ListHartakarunResponse

class HartakarunAdapter(
    private var list: List<ListHartakarunResponse.Hartakarun>,
) : RecyclerView.Adapter<HartakarunAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardHartakarunBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardHartakarunBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            with(list[position]){
                tvTitle.text = this.title
                tvDescription.text = this.description
                tvPoint.text = this.point.toString()
            }
        }

//        holder.itemView.setOnClickListener {
//            val intent = Intent(it.context, DetailHartakarunActivity::class.java)
//            intent.putExtra(DetailHartakarunActivity.RECIPES_DATA, list[position])
//            it.context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}