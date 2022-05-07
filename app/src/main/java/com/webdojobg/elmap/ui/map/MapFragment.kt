package com.webdojobg.elmap.ui.map

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.webdojobg.elmap.R
import com.webdojobg.elmap.data.models.Station
import com.webdojobg.elmap.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
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
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val stationInfo = root.findViewById<LinearLayout>(R.id.stationsInfo)
        viewModel.isStationInfoVisible.observe(
            viewLifecycleOwner, Observer {
                visible ->
                if(visible){
                    stationInfo.visibility = View.VISIBLE
                }else{
                    stationInfo.visibility = View.GONE
                }
            }
        )

        viewModel.selectedStation.observe(
            viewLifecycleOwner, Observer {
                selectedStation ->
                root.findViewById<TextView>(R.id.station_address).text = selectedStation.address
                root.findViewById<TextView>(R.id.station_name).text = selectedStation.name
            }
        )

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

        root.findViewById<Button>(R.id.stationsInfo_hide).setOnClickListener {
            buttonView -> viewModel.isStationInfoVisible.value = false
        }

        root.findViewById<Button>(R.id.station_navigate).setOnClickListener {
            // Create a Uri from an intent string. Use the result to create an Intent.
            val gmmIntentUri =
                Uri.parse("google.navigation:q=${viewModel.selectedStation.value?.address}}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

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
        _gMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // Open station details
        viewModel.selectStation(marker.title!!)

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }
}