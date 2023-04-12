package com.example.assignment.ui.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.assignment.R
import com.example.assignment.adapter.CityAdapter
import com.example.assignment.adapter.CustomClickListener
import com.example.assignment.api.response.bycity.ResponseByCityNameItem
import com.example.assignment.api.response.bylatlong.Coord
import com.example.assignment.databinding.FragmentSearchBinding
import com.example.assignment.di.MyPreference
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel by viewModels<SearchViewModel>()
    private var adapter: CityAdapter? = null

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var PERMISSION_ID = 44
    @Inject
    lateinit var pref: MyPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchBinding.bind(view)
        if (!TextUtils.isEmpty(pref.getLat()) && !TextUtils.isEmpty(pref.getLng())){
            viewModel.callWeatherApiByLocation(pref.getLat()!!, pref.getLng()!!)
        } else {
            fetchLocation()
        }
        adapter = CityAdapter(requireContext()
        ) { f ->
            Toast.makeText(
                context, "You clicked " + f?.name,
                Toast.LENGTH_LONG
            ).show();
            val bundle = Bundle()
            bundle.putParcelable("location", f?.lon?.let { Coord(lat = f.lat, lon = it) });
            Navigation.findNavController(view)
                .navigate(R.id.action_searchFragment_to_detailFragment, bundle)
            pref.setLat(f?.lat.toString())
            pref.setLng(f?.lon.toString())
        }.also {
            binding.recyclerView.adapter = it
            it.notifyDataSetChanged()
        }

        viewModel.searchByCityResponse.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            Log.d("response", "$it")
            adapter?.setData(it)
        }

        viewModel.searchByLatLong.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            Log.d("response by lat lng", "$it")
            val responseByCity = ResponseByCityNameItem(
                    country = "null",
                    lat = it.coord.lat,
                    lon = it.coord.lon,
                    state = "null",
                    name = it.name
            )
            val list: MutableList<ResponseByCityNameItem> = mutableListOf()
            list.add(responseByCity)
            adapter?.setData(list)
        }
        setHasOptionsMenu(true)
    }
    var mFusedLocationClient: FusedLocationProviderClient? = null

    private fun fetchLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // method to get the location
        getLastLocation();
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient!!.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    Log.d("location is : ", "${location?.latitude}, ${location?.longitude}")
                    binding.progressBar.visibility = View.VISIBLE
                    location?.let { viewModel.callWeatherApiByLocation(it) }

                    pref.setLat(location?.latitude.toString())
                    pref.setLng(location?.longitude.toString())
                }
            } else {
                Toast.makeText(requireContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions()
        }
    }


    // method to check for permissions
    private fun checkPermissions(): Boolean {
        return  ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private fun requestPermissions() {
        requestPermissions(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )
    }

    // method to check
    // if location is enabled
    private fun isLocationEnabled(): Boolean {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.length >= 3) {
                    binding.recyclerView.scrollToPosition(0)
                    binding.progressBar.visibility = View.VISIBLE
                    viewModel.callWeatherApi(query)
                    searchView.clearFocus()
                } else
                    Toast.makeText(
                        activity,
                        "Type at least 3 characters to search",
                        Toast.LENGTH_SHORT
                    ).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}