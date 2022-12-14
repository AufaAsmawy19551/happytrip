package com.example.happytrip.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.happytrip.DetailMissionActivity
import com.example.happytrip.MissionActivity
import com.example.happytrip.databinding.CardMissionBinding
import com.example.happytrip.helper.Navigator
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO
import com.example.happytrip.restClient.retrofitInstance.TravelerRetrofit
import com.example.happytrip.restClient.traveler.apiInterface.TravelerApi
import com.example.happytrip.restClient.traveler.response.mission.DetailMissionResponse
import com.example.happytrip.restClient.traveler.response.mission.ListMissionResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MissionAdapter (
    private var list: List<ListMissionResponse.Mission>,
) : RecyclerView.Adapter<MissionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardMissionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            with(list[position]){
                tvTitle.text = this.title
                tvPoint.text = this.point.toString()
                tvProgress.text = "${this.visitedWisata.toString()}/${this.wisata.toString()}"
            }
        }

        holder.itemView.setOnClickListener {
            TravelerRetrofit()
                .getRetroClientInstance()
                .create(TravelerApi::class.java)
                .detailMission(list[position].id as Int)
                .enqueue(
                    object: Callback<DetailMissionResponse> {
                        override fun onFailure(call: Call<DetailMissionResponse>, t: Throwable){
                            Log.e("Error", t.message.toString())
                        }
                        override fun onResponse(call: Call<DetailMissionResponse>, response: Response<DetailMissionResponse>) {
                            if (response.isSuccessful) {
                                val data = response.body()
                                TravelerResponseDTO.detailMission = data?.data
                                Navigator.changeActivity(it.context, DetailMissionActivity::class.java)
                                Toast.makeText(it.context, data?.message?.get(0).toString(), Toast.LENGTH_LONG).show()
                            } else {
                                try {
                                    val jObjError = JSONObject(response.errorBody()!!.string())
                                    Toast.makeText(it.context, jObjError.getJSONArray("message").get(0).toString(), Toast.LENGTH_LONG).show()
                                } catch (e: Exception) {
                                    Toast.makeText(it.context, e.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}