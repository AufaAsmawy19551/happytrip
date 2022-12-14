package com.example.happytrip

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.happytrip.databinding.ActivityLoginBinding
import com.example.happytrip.helper.Navigator
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO
import com.example.happytrip.restClient.retrofitInstance.TravelerRetrofit
import com.example.happytrip.restClient.traveler.apiInterface.TravelerApi
import com.example.happytrip.restClient.traveler.response.auth.LoginResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init(){
        binding.btnLogin.setOnClickListener { login() }
        binding.btnRegister.setOnClickListener {
            Navigator.changeActivity(this@LoginActivity, RegisterActivity::class.java)
        }
    }


    fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

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
                            Navigator.changeActivity(this@LoginActivity, MainActivity::class.java)
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