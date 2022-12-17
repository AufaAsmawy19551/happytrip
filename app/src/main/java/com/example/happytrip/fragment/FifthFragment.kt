package com.example.happytrip.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.happytrip.LoginActivity
import com.example.happytrip.databinding.FragmentFifthBinding
import com.example.happytrip.helper.Navigator
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO
import com.example.happytrip.restClient.retrofitInstance.TravelerRetrofit
import com.example.happytrip.restClient.traveler.apiInterface.TravelerApi
import com.example.happytrip.restClient.traveler.response.auth.LogoutResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FifthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FifthFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var _binding: FragmentFifthBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFifthBinding.inflate(inflater, container, false)
        val view = binding.root

        init()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun init(){
        binding.btnLogout.setOnClickListener { logout() }
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

                            requireActivity().getSharedPreferences("DATA", Context.MODE_PRIVATE)
                                .edit()
                                .putString("token", null)
                                .apply()
                            Toast.makeText(requireContext(), data?.message?.get(0).toString(), Toast.LENGTH_LONG).show()
                            Log.e("token", TravelerResponseDTO.token.toString())
                            Navigator.changeActivity(requireContext(), LoginActivity::class.java)
                        } else {
                            try {
                                val jObjError = JSONObject(response.errorBody()!!.string())
                                Toast.makeText(requireContext(), jObjError.getJSONArray("message").get(0).toString(), Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FifthFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FifthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}