package com.example.happytrip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.happytrip.databinding.ActivityLoginBinding
import com.example.happytrip.databinding.ActivityRegisterBinding
import com.example.happytrip.helper.Navigator
import com.example.happytrip.restClient.retrofitInstance.TravelerRetrofit
import com.example.happytrip.restClient.traveler.apiInterface.TravelerApi
import com.example.happytrip.restClient.traveler.response.register.RegisterResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init(){
        binding.btnRegister.setOnClickListener { register() }
    }

    fun register(){
        val name = binding.etUsername.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val passwordConfirmation = binding.etPassword.text.toString().trim()

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
                            Navigator.changeActivity(this@RegisterActivity, LoginActivity::class.java)
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

    fun back(view: View) {
        Navigator.changeActivity(this@RegisterActivity, LoginActivity::class.java)
    }
}