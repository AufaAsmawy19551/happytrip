package com.example.happytrip.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.happytrip.DetailWisataActivity
import com.example.happytrip.R
import com.example.happytrip.adapter.WisataAdapter
import com.example.happytrip.databinding.FragmentFirstBinding
import com.example.happytrip.helper.Navigator
import com.example.happytrip.restClient.responseDTO.TravelerResponseDTO
import com.example.happytrip.restClient.retrofitInstance.TravelerRetrofit
import com.example.happytrip.restClient.traveler.apiInterface.TravelerApi
import com.example.happytrip.restClient.traveler.response.wisata.DetailWisataResponse
import com.example.happytrip.restClient.traveler.response.wisata.ListWisataResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment(), OnMapReadyCallback{
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

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

    lateinit var useLocationMarker: Marker

    private var gson = Gson()

    @SuppressLint("MissingPermission")
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
                .zoom(9.4f)
                .bearing(0f)
                .tilt(45f)
                .build()

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null)

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            if (checkPermissions()) {
                if (isLocationEnabled()) {
                    mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                        val location: Location? = task.result
                        if (location != null) {
                            val geocoder = Geocoder(requireContext(), Locale.getDefault())
                            var userLocation: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            useLocationMarker = mMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(userLocation[0].latitude, userLocation[0].longitude))
                                    .title("Lokasi Kamu")
                                    .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_user_location))
                            )!!

//                        Toast.makeText(requireContext(), "Latitude\n${list[0].latitude}" + "Longitude\n${list[0].longitude}", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Please turn on location", Toast.LENGTH_LONG).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            } else {
                requestPermissions()
            }

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
                                                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_location_visited))
                                                .snippet((gson.toJson(wisata)))
                                        )
                                    }else{
                                        mMap.addMarker(
                                            MarkerOptions()
                                                .position(LatLng(location!![0].toDouble(), location!![1].toDouble()))
                                                .title(wisata.title)
                                                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_location_unvisited))
                                                .snippet((gson.toJson(wisata)))
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

            mMap.setInfoWindowAdapter(WisataAdapter(requireContext()))

            mMap.setOnInfoWindowClickListener { marker ->
                if(! marker.equals(useLocationMarker)){
                    TravelerRetrofit()
                        .getRetroClientInstance()
                        .create(TravelerApi::class.java)
                        .detailWisata(gson.fromJson(marker.snippet, ListWisataResponse.Wisata::class.java)!!.id!!)
                        .enqueue(
                            object: Callback<DetailWisataResponse> {
                                override fun onFailure(call: Call<DetailWisataResponse>, t: Throwable){
                                    Log.e("Error", t.message.toString())
                                }
                                override fun onResponse(call: Call<DetailWisataResponse>, response: Response<DetailWisataResponse>) {
                                    if (response.isSuccessful) {
                                        val data = response.body()
                                        TravelerResponseDTO.detailWisata = data?.data
                                        Navigator.changeActivity(requireContext(), DetailWisataActivity::class.java)
                                        Toast.makeText(requireContext(), data?.message?.get(0).toString(), Toast.LENGTH_LONG).show()
                                    } else {
                                        try {
                                            val jObjError = JSONObject(response.errorBody()!!.string())
                                            Toast.makeText(requireContext(), jObjError.getJSONArray("message").get(0).toString(), Toast.LENGTH_LONG).show()
                                        } catch (e: java.lang.Exception) {
                                            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        )
                }
            }

//            mMap.addMarker(
//                MarkerOptions()
//                    .position(LatLng(-7.7628136, 110.1161626))
//                    .title("Sungai Mudal")
//                    .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_location_unvisited))
//                    .snippet(
//                        "image: qwertyui\n" +
//                                "rating: 4.5\n" +
//                                "progress: 0/3"
//                    )
//            )
//
//            mMap.addMarker(
//                MarkerOptions()
//                    .position(LatLng(-7.7926455, 110.365846))
//                    .title("Mailoboro")
//                    .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_location_unvisited))
//                    .snippet(
//                        "image: qwertyui\n" +
//                                "rating: 4.5\n" +
//                                "progress: 0/3"
//                    )
//            )
//
//            mMap.addMarker(
//                MarkerOptions()
//                    .position(LatLng(-7.8458407, 110.4798457))
//                    .title("Bukit Bintang")
//                    .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_location_unvisited))
//                    .snippet(
//                        "image: qwertyui\n" +
//                                "rating: 4.5\n" +
//                                "progress: 0/3"
//                    )
//            )
//
//            mMap.addMarker(
//                MarkerOptions()
//                    .position(LatLng(-7.75074552, 110.49519205))
//                    .title("Candi Prambanan")
//                    .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_location_unvisited))
//                    .snippet(
//                        "image: qwertyui\n" +
//                                "rating: 4.5\n" +
//                                "progress: 0/3"
//                    )
//            )
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bitmapDescriptorFromVector(context: Context?, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        var userLocation: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        mMap.addMarker(
                            MarkerOptions()
                                .position(LatLng(userLocation[0].latitude, userLocation[0].longitude))
                                .title("Mailoboro")
                                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_user_location))
                        )

//                        Toast.makeText(requireContext(), "Latitude\n${list[0].latitude}" + "Longitude\n${list[0].longitude}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
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