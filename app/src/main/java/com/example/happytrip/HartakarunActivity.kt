package com.example.happytrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happytrip.adapter.HartakarunAdapter
import com.example.happytrip.adapter.TestAdapter
import com.example.happytrip.databinding.ActivityHartakarunBinding
import com.example.happytrip.databinding.ActivityMainBinding
import com.example.happytrip.model.Test
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO
import com.example.happytrip.restClient.retrofitInstance.TravelerRetrofit
import com.example.happytrip.restClient.traveler.apiInterface.TravelerApi
import com.example.happytrip.restClient.traveler.response.hartakarun.ListHartakarunResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class HartakarunActivity : AppCompatActivity() {

    private var _binding : ActivityHartakarunBinding? = null
    private val binding get() = _binding!!
    private var hartakaruns: List<ListHartakarunResponse.Hartakarun>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHartakarunBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        listHartakarun()
//        init()

        Log.e("onCreate, size", hartakaruns?.size.toString())

        var rvAdapter = hartakaruns?.let { HartakarunAdapter(it) }
        Log.e("onCreate, adapter", rvAdapter.toString())

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvHartakarun.setLayoutManager(layoutManager)
        binding.rvHartakarun.setHasFixedSize(true);
        binding.rvHartakarun.adapter = rvAdapter
    }

    fun listHartakarun() {
        TravelerRetrofit()
            .getRetroClientInstance()
            .create(TravelerApi::class.java)
            .listHartakarun()
            .enqueue(
                object: Callback<ListHartakarunResponse> {
                    override fun onFailure(call: Call<ListHartakarunResponse>, t: Throwable){
                        Log.e("Error", t.message.toString())
                    }
                    override fun onResponse(call: Call<ListHartakarunResponse>, response: Response<ListHartakarunResponse>) {
                        if (response.isSuccessful) {
                            val data = response.body()

                            hartakaruns = data?.data

                            Log.e("in method, size",hartakaruns?.size.toString())

                            for (hartakarun in hartakaruns!!){
                                Log.e("in method, title", hartakarun.title.toString())
                            }

                            Toast.makeText(getApplicationContext(), data?.message?.get(0).toString(), Toast.LENGTH_LONG).show()
                        } else {
                            try {
                                val jObjError = JSONObject(response.errorBody()!!.string())
                                Toast.makeText(getApplicationContext(), jObjError.getJSONArray("message").get(0).toString(), Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                                Toast.makeText(getApplicationContext(), e.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            )
    }

    fun init(){
//        hartakaruns = listOf<Test>(
//            Test("diskon 10 %", "diskon 10 %", "100"),
//            Test("diskon 20 %", "diskon 20 %", "100"),
//            Test("diskon 30 %", "diskon 30 %", "100"),
//            Test("diskon 40 %", "diskon 40 %", "100"),
//            Test("diskon 50 %", "diskon 50 %", "100"),
//            Test("diskon 60 %", "diskon 60 %", "100"),
//            Test("diskon 70 %", "diskon 70 %", "100"),
//            Test("diskon 80 %", "diskon 80 %", "100"),
//            Test("diskon 90 %", "diskon 90 %", "100"),
//            Test("diskon 40 %", "diskon 40 %", "100"),
//            Test("diskon 50 %", "diskon 50 %", "100"),
//            Test("diskon 60 %", "diskon 60 %", "100"),
//            Test("diskon 70 %", "diskon 70 %", "100"),
//            Test("diskon 80 %", "diskon 80 %", "100"),
//            Test("diskon 90 %", "diskon 90 %", "100"),
//        )
    }

//    // on destroy of view make
//    // the binding reference to null
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
}