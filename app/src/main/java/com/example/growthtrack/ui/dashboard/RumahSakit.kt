package com.example.growthtrack.ui.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.growthtrack.R
import com.example.growthtrack.api.ApiService
import com.example.growthtrack.api.Hospital
import com.example.growthtrack.api.PlacesResponse
import com.example.growthtrack.api.RetrofitInstance

import com.example.growthtrack.databinding.FragmentRumahsakitBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RumahSakit : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentRumahsakitBinding? = null
    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap
    private lateinit var placesClient: ApiService.PlacesApiService
    private val hospitalList = mutableListOf<Hospital>()
    private lateinit var hospitalAdapter: HospitalAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentRumahsakitBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
//        hospitalAdapter = HospitalAdapter(hospitalList)
//        binding.RV1.apply {
//            adapter = hospitalAdapter
//        }

        // Initialize Places API
        placesClient = RetrofitInstance.api

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Check location permission
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }
        mMap.isMyLocationEnabled = true

        // Get the current location of the device
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.addMarker(MarkerOptions().position(currentLatLng).title("You are here"))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                findNearbyHospitals(location)
            }
        }
    }

    private fun findNearbyHospitals(location: Location) {
        val locationStr = "${location.latitude},${location.longitude}"
        val radius = 1000 // radius in meters (e.g., 5 km)
        val type = "hospital"
        val apiKey = getString(R.string.google_maps_key)

        placesClient.getNearbyPlaces(locationStr, radius, type, apiKey)
            .enqueue(object : Callback<PlacesResponse> {
                override fun onResponse(call: Call<PlacesResponse>, response: Response<PlacesResponse>) {
                    if (response.isSuccessful) {
                        hospitalList.clear()
                        Log.d("Mapnye", response.body()?.results.toString())
//                        val rslist = response.body()?.results
//
//                        val rcv: RecyclerView? = binding.RV1
//                        val adapter = HospitalAdapter(rslist)
//                        rcv?.adapter = adapter
                        response.body()?.results?.forEach { place ->
                            val placeLatLng = LatLng(place.geometry.location.lat, place.geometry.location.lng)
                            val placeLocation = Location("").apply {
                                latitude = place.geometry.location.lat
                                longitude = place.geometry.location.lng

                            }
                            val distance = location.distanceTo(placeLocation)

//                             Log place type, name, and distance
                            hospitalList.add(Hospital(place.name, distance))
//
                            Log.d("Mapnye", "Place found: ${place},dan ${type}, Distance: ${distance} meters")

                            mMap.addMarker(MarkerOptions().position(placeLatLng).title(place.name))
                        }
                        val rcv: RecyclerView? = binding.RV1
                        val adapter = HospitalAdapter(hospitalList)
                        rcv?.adapter = adapter

                    }
                    else {
                        Log.e("Mapnye", "Response was not successful: ${response.message()}")
                    }

                }

                override fun onFailure(call: Call<PlacesResponse>, t: Throwable) {
                    Log.e("Mapnye", "API call failed: ${t.message}")
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permission granted
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mMap.isMyLocationEnabled = true
                    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            val currentLatLng = LatLng(location.latitude, location.longitude)
                            mMap.addMarker(MarkerOptions().position(currentLatLng).title("You are here"))
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                            findNearbyHospitals(location)
                        }
                    }
                }
            } else {
                // Permission denied
                Log.d("Mapnye", "Location permission denied")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hospitalList.clear()
        _binding = null
    }
}