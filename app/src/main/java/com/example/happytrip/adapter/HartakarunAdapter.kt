package com.example.happytrip.adapter

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

//class HartakarunAdapter (private val list: ArrayList<ListHartakarunResponse.Hartakarun>):
//    RecyclerView.Adapter<HartakarunAdapter.HartakarunViewHolder>(){
//    inner class HartakarunViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        fun bind(hartakarun: ListHartakarunResponse.Hartakarun){
//            with(itemView){
//                val text = "id: ${hartakarun.id}\n" +
//                        "title: ${hartakarun.title}\n" +
//                        "description: ${hartakarun.description}\n" +
//                        "point: ${hartakarun.point}\n" +
//                        "redeemed: ${hartakarun.redeemed}"
//                tvText.text = text
//            }
//        }
//    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HartakarunViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_hartakarun, parent, false)
//        return HartakarunViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: HartakarunViewHolder, position: Int) {
//        holder.bind(list[position])
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//}
