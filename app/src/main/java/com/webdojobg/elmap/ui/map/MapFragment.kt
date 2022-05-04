package com.webdojobg.elmap.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.webdojobg.elmap.R
import com.webdojobg.elmap.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    private lateinit var _mMap : GoogleMap
    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap -> _mMap = googleMap
            updateMap()
        }

        return root
    }

    private fun updateMap() {
        val sydney = LatLng(-34.0, 151.0)
        _mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        _mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10.0F))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}