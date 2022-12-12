package com.example.happytrip

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.happytrip.databinding.ActivityTestBinding
import com.example.happytrip.helper.Navigator
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO
import com.example.happytrip.restClient.retrofitInstance.TravelerRetrofit
import com.example.happytrip.restClient.traveler.apiInterface.TravelerApi
import com.example.happytrip.restClient.traveler.response.auth.LoginResponse
import com.example.happytrip.restClient.traveler.response.auth.LogoutResponse
import com.example.happytrip.restClient.traveler.response.auth.RefreshTokenResponse
import com.example.happytrip.restClient.traveler.response.auth.UserResponse
import com.example.happytrip.restClient.traveler.response.hartakarun.ListHartakarunResponse
import com.example.happytrip.restClient.traveler.response.mission.ListMissionResponse
import com.example.happytrip.restClient.traveler.response.register.RegisterResponse
import com.example.happytrip.restClient.traveler.response.scan.ScanResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAction()
    }

    fun initAction(){
        TravelerResponseDTO.token = getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("token", null)

        binding.btnRegister.setOnClickListener{register()}
        binding.btnLogin.setOnClickListener{login()}
        binding.btnUser.setOnClickListener{user()}
        binding.btnRefreshToken.setOnClickListener{refreshToken()}
        binding.btnLogout.setOnClickListener{logout()}
        binding.btnHartakarun.setOnClickListener{goToHartakarun()}
        binding.btnMission.setOnClickListener{goToMission()}
        binding.btnScan.setOnClickListener{scan()}
    }

    fun register(){
//        val email = binding.etEmail.text.toString().trim()
//        val password = binding.etPassword.text.toString().trim()
        val name = "Aufa"
        val email = "aufa@gmail.com"
        val password = "password"
        val passwordConfirmation = "password"

        TravelerRetrofit()
            .getRetroClientInstance()
            .create(TravelerApi::class.java)
            .registerStore(name, email, password, passwordConfirmation)
            .enqueue(
                object: Callback<RegisterResponse> {
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable){
                        Log.e("Error", t.message.toString())
                    }
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            val data = response.body()
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

    fun login() {
//        val email = binding.etEmail.text.toString().trim()
//        val password = binding.etPassword.text.toString().trim()
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

    fun user() {
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

    fun refreshToken() {
        TravelerRetrofit()
            .getRetroClientInstance()
            .create(TravelerApi::class.java)
            .authRefreshToken()
            .enqueue(
                object: Callback<RefreshTokenResponse> {
                    override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable){
                        Log.e("Error", t.message.toString())
                    }
                    override fun onResponse(call: Call<RefreshTokenResponse>, response: Response<RefreshTokenResponse>) {
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

    fun logout() {
        TravelerRetrofit()
            .getRetroClientInstance()
            .create(TravelerApi::class.java)
            .authLogout()
            .enqueue(
                object: Callback<LogoutResponse> {
                    override fun onFailure(call: Call<LogoutResponse>, t: Throwable){
                        Log.e("Error", t.message.toString())
                    }
                    override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            TravelerResponseDTO.traveler = null
                            TravelerResponseDTO.token = null

                            getSharedPreferences("DATA", Context.MODE_PRIVATE)
                                .edit()
                                .putString("token", null)
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


    fun goToHartakarun() {
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

                            TravelerResponseDTO.hartakaruns = data?.data

                            Navigator.changeActivity(this@TestActivity,HartakarunActivity::class.java)

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

    fun goToMission() {
        TravelerRetrofit()
            .getRetroClientInstance()
            .create(TravelerApi::class.java)
            .listMission()
            .enqueue(
                object: Callback<ListMissionResponse> {
                    override fun onFailure(call: Call<ListMissionResponse>, t: Throwable){
                        Log.e("Error", t.message.toString())
                    }
                    override fun onResponse(call: Call<ListMissionResponse>, response: Response<ListMissionResponse>) {
                        if (response.isSuccessful) {
                            val data = response.body()

                            TravelerResponseDTO.missions = data?.data

                            for(mission in data?.data!!){
                                Log.e("visitedWisata", mission?.visitedWisata.toString())
                            }

                            Navigator.changeActivity(this@TestActivity,MissionActivity::class.java)

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

    fun scan() {
        var code = "512cf2129789cdbf73649a179ba9b2e0279a4e07c411df27f69663d6f775c66c"
        Log.e("scan Code", code)
        TravelerRetrofit()
            .getRetroClientInstance()
            .create(TravelerApi::class.java)
            .scan(code)
            .enqueue(
                object: Callback<ScanResponse> {
                    override fun onFailure(call: Call<ScanResponse>, t: Throwable){
                        Log.e("Error", t.message.toString())
                    }
                    override fun onResponse(call: Call<ScanResponse>, response: Response<ScanResponse>) {
                        if (response.isSuccessful) {
                            val data = response.body()

                            Log.e("Response Code", response.code().toString())
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
}