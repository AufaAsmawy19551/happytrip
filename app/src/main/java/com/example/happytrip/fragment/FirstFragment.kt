package com.example.happytrip.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happytrip.R
import com.example.happytrip.adapter.MissionAdapter
import com.example.happytrip.databinding.FragmentFirstBinding
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO
import com.example.happytrip.restClient.retrofitInstance.TravelerRetrofit
import com.example.happytrip.restClient.traveler.apiInterface.TravelerApi
import com.example.happytrip.restClient.traveler.response.mission.ListMissionResponse
import com.example.happytrip.restClient.traveler.response.wisata.ListWisataResponse
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
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
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("run", "From onCreate FirstFragment")
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("run", "From onCreateView MainActivity")

        // Inflate the layout for this fragment
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val view = binding.root

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment!!.getMapAsync { mMap ->
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

            mMap.clear() //clear old markers

            val googlePlex = CameraPosition.builder()
                .target(LatLng(-7.87304695, 110.42428745))
                .zoom(10f)
                .bearing(0f)
                .tilt(45f)
                .build()

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null)
//            var icon = ResourcesCompat.getDrawable(requireActivity().resources, androidx.loader.R.drawable.notification_icon_background)

            TravelerRetrofit()
                .getRetroClientInstance()
                .create(TravelerApi::class.java)
                .listWisata()
                .enqueue(
                    object : Callback<ListWisataResponse> {
                        override fun onFailure(call: Call<ListWisataResponse>, t: Throwable) {
                            Log.e("Error", t.message.toString())
                        }

                        override fun onResponse(call: Call<ListWisataResponse>, response: Response<ListWisataResponse>) {
                            if (response.isSuccessful) {
                                val data = response.body()

                                TravelerResponseDTO.listWisata = data?.data

                                for (wisata: ListWisataResponse.Wisata in TravelerResponseDTO.listWisata!!) {
                                    var location = wisata.location?.split(",")

                                    if(wisata.isVisited!!){
                                        mMap.addMarker(
                                            MarkerOptions()
                                                .position(LatLng(location!![0].toDouble(), location!![1].toDouble()))
                                                .title(wisata.title)
                                                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_location_blue))
                                        )
                                    }else{
                                        mMap.addMarker(
                                            MarkerOptions()
                                                .position(LatLng(location!![0].toDouble(), location!![1].toDouble()))
                                                .title(wisata.title)
                                                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_location_red))
                                        )
                                    }
                                }

                                Toast.makeText(requireContext(), data?.message?.get(0).toString(), Toast.LENGTH_LONG).show()
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

//            mMap.addMarker(
//                MarkerOptions()
//                    .position(LatLng(-7.7628136, 110.1161626))
//                    .title("Sungai Mudal")
//                    .snippet("0/2")
//            )
//
//            mMap.addMarker(
//                MarkerOptions()
//                    .position(LatLng(-8.02539498, 110.32875584))
//                    .title("Pantai Parangtritis")
//                    .snippet("0/4")
//            )
//
//            mMap.addMarker(
//                MarkerOptions()
//                    .position(LatLng(-7.7926455, 110.365846))
//                    .title("Mailoboro")
//                    .snippet("0/5")
//            )
//
//            mMap.addMarker(
//                MarkerOptions()
//                    .position(LatLng(-7.8458407, 110.4798457))
//                    .title("Bukit Bintang")
//                    .snippet("0/3")
//            )
//
//            mMap.addMarker(
//                MarkerOptions()
//                    .position(LatLng(-7.75074552, 110.49519205))
//                    .title("Candi Prambanan")
//                    .snippet("0/4")
//            )
        }

        return view
    }

    private fun bitmapDescriptorFromVector(context: Context?, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

        fun listWisata() {
            TravelerRetrofit()
                .getRetroClientInstance()
                .create(TravelerApi::class.java)
                .listWisata()
                .enqueue(
                    object : Callback<ListWisataResponse> {
                        override fun onFailure(call: Call<ListWisataResponse>, t: Throwable) {
                            Log.e("Error", t.message.toString())
                        }

                        override fun onResponse(
                            call: Call<ListWisataResponse>,
                            response: Response<ListWisataResponse>
                        ) {
                            if (response.isSuccessful) {
                                val data = response.body()

                                TravelerResponseDTO.listWisata = data?.data

                                for (wisata: ListWisataResponse.Wisata in TravelerResponseDTO.listWisata!!) {

                                    var location = wisata.location?.split(",")
                                    mMap.addMarker(
                                        MarkerOptions()
                                            .position(
                                                LatLng(
                                                    location!![0].toDouble(),
                                                    location!![1].toDouble()
                                                )
                                            )
                                            .title(wisata.title)
                                    )

                                    Log.e("latitide", location!![0].toDouble().toString())
                                    Log.e("longitue", location!![1].toDouble().toString())
                                }

                                Toast.makeText(
                                    requireContext(),
                                    data?.message?.get(0).toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                try {
                                    val jObjError = JSONObject(response.errorBody()!!.string())
                                    Toast.makeText(
                                        requireContext(),
                                        jObjError.getJSONArray("message").get(0).toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } catch (e: Exception) {
                                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG)
                                        .show()
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
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-7.7628136, 110.1161626)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}