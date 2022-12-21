package com.example.happytrip

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.happytrip.databinding.ActivityMainBinding
import com.example.happytrip.helper.Navigator
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO
import com.example.happytrip.restClient.retrofitInstance.TravelerRetrofit
import com.example.happytrip.restClient.traveler.apiInterface.TravelerApi
import com.example.happytrip.restClient.traveler.response.auth.LoginResponse
import com.example.happytrip.restClient.traveler.response.auth.UserResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        TravelerResponseDTO.token = getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("token", null)
//        login()
        isUser()
        Log.e("run", "From onCreate MainActivity")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.fragment)
        val appBarConfiguration =  AppBarConfiguration(
            setOf(
                R.id.firstFragment,
                R.id.secondFragment,
                R.id.thridFragment,
                R.id.fourthFragment,
                R.id.fifthFragment
            )
        )
        //apabila ingin menghilangkan appbar(title) maka kode dibawah harus di comment
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

    }

    fun login() {
        val email = "aufa@gmail.com"
        val password = "password"

        TravelerRetrofit()
            .getRetroClientInstance()
            .create(TravelerApi::class.java)
            .authLogin(email, password)
            .enqueue(
                object: Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable){
                        Log.e("Error", t.message.toString())
                    }
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            TravelerResponseDTO.traveler = data?.user
                            TravelerResponseDTO.token = data?.token.toString()

                            getSharedPreferences("DATA", Context.MODE_PRIVATE)
                                .edit()
                                .putString("token", data?.token.toString())
                                .apply()
                            Toast.makeText(getApplicationContext(), data?.message?.get(0).toString(), Toast.LENGTH_LONG).show()
                            Log.e("token", TravelerResponseDTO.token.toString())
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

    fun isUser() {
        TravelerRetrofit()
            .getRetroClientInstance()
            .create(TravelerApi::class.java)
            .authUser()
            .enqueue(
                object: Callback<UserResponse> {
                    override fun onFailure(call: Call<UserResponse>, t: Throwable){
                        Log.e("Error", t.message.toString())
                    }
                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            TravelerResponseDTO.traveler = data?.user
                            Toast.makeText(getApplicationContext(), data?.message?.get(0).toString(), Toast.LENGTH_LONG).show()
                        } else {
                            try {
                                val jObjError = JSONObject(response.errorBody()!!.string())
                                Toast.makeText(getApplicationContext(), jObjError.getJSONArray("message").get(0).toString(), Toast.LENGTH_LONG).show()
                                Navigator.changeActivity(this@MainActivity, LoginActivity::class.java)
                            } catch (e: Exception) {
                                Toast.makeText(getApplicationContext(), e.message, Toast.LENGTH_LONG).show()
                                Navigator.changeActivity(this@MainActivity, LoginActivity::class.java)
                            }
                        }
                    }
                }
            )
    }
}