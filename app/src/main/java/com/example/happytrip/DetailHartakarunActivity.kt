package com.example.happytrip

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.happytrip.databinding.ActivityDetailHartakarunBinding
import com.example.happytrip.helper.Navigator
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO
import com.example.happytrip.restClient.retrofitInstance.TravelerRetrofit
import com.example.happytrip.restClient.traveler.apiInterface.TravelerApi
import com.example.happytrip.restClient.traveler.response.claimHartakarun.ClaimHartakarunResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailHartakarunActivity : AppCompatActivity() {

    private var _binding : ActivityDetailHartakarunBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailHartakarunBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
    }

    fun init(){
        binding.btnClaim.setOnClickListener { claim() }
        binding.tvTitle.text = TravelerResponseDTO.detailHartakarun?.title
        binding.tvPoint.text = TravelerResponseDTO.detailHartakarun?.point.toString()
        binding.tvDescription.text = TravelerResponseDTO.detailHartakarun?.description
    }

    fun claim(){
        TravelerRetrofit()
            .getRetroClientInstance()
            .create(TravelerApi::class.java)
            .claimHartakarun(TravelerResponseDTO.detailHartakarun?.id!!)
            .enqueue(
                object: Callback<ClaimHartakarunResponse> {
                    override fun onFailure(call: Call<ClaimHartakarunResponse>, t: Throwable){
                        Log.e("Error", t.message.toString())
                    }
                    override fun onResponse(call: Call<ClaimHartakarunResponse>, response: Response<ClaimHartakarunResponse>) {
                        if (response.isSuccessful) {
                            val data = response.body()

                            TravelerResponseDTO.listHartakarun = data?.data

                            Toast.makeText(this@DetailHartakarunActivity, data?.message?.get(0).toString(), Toast.LENGTH_LONG).show()

//                            val manager: FragmentManager = supportFragmentManager
//                            val transaction: FragmentTransaction = manager.beginTransaction()
//                            transaction.replace(
//                                R.id.fragment,
//                                Fragment(R.layout.fragment_fourth)
//                            )
//                            transaction.addToBackStack(null)
//                            transaction.commit()
//                            this@DetailHartakarunActivity.onBackPressed()
                            Navigator.changeActivity(this@DetailHartakarunActivity, MainActivity::class.java)
                        } else {
                            try {
                                val jObjError = JSONObject(response.errorBody()!!.string())
                                Toast.makeText(this@DetailHartakarunActivity, jObjError.getJSONArray("message").get(0).toString(), Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                                Toast.makeText(this@DetailHartakarunActivity, e.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            )
    }
}