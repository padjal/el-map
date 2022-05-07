package com.webdojobg.elmap.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.webdojobg.elmap.R
import com.webdojobg.elmap.data.models.Station
import com.webdojobg.elmap.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var _gMap : GoogleMap
    private var _binding: FragmentMapBinding? = null
    val viewModel: MapViewModel by viewModels()
    val stationsList = ArrayList<Station>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mapViewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.stations.observe(
            viewLifecycleOwner, Observer {
                stations -> // update ui
                // add marker to map
                for (station in stations){
                    addMarker(station)
                }
                Log.i("mapViewModel",stations.toString())
            }
        )

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return root
    }

    private fun addMarker(station: Station) {
        _gMap.addMarker(
            MarkerOptions()
                .position(station.location)
                .title(station.name)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        _gMap = googleMap
        // set current position to moscow
        val moscow = LatLng(55.7535609, 37.6187217)
        _gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moscow, 12.0F))
    }
}