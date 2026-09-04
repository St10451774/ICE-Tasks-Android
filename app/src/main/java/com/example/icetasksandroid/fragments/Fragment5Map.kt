package com.example.icetasksandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.icetasksandroid.R
import com.example.icetasksandroid.databinding.Fragment5MapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Fragment5Map : Fragment(), OnMapReadyCallback {

    private var _binding: Fragment5MapBinding? = null
    private val binding get() = _binding!!
    private var googleMap: GoogleMap? = null
    private var isMapVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Fragment5MapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize map fragment
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        // Setup toggle button
        binding.buttonToggleMap.setOnClickListener {
            toggleMapVisibility()
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // Initially hide the map
        binding.mapContainer.visibility = View.GONE
        isMapVisible = false
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Set default location (San Francisco)
        val sanFrancisco = LatLng(37.7749, -122.4194)
        googleMap?.addMarker(
            MarkerOptions()
                .position(sanFrancisco)
                .title("San Francisco")
                .snippet("Welcome to San Francisco!")
        )
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(sanFrancisco, 12f))

        // Add more markers for demonstration
        val losAngeles = LatLng(34.0522, -118.2437)
        googleMap?.addMarker(
            MarkerOptions()
                .position(losAngeles)
                .title("Los Angeles")
                .snippet("City of Angels")
        )

        val newYork = LatLng(40.7128, -74.0060)
        googleMap?.addMarker(
            MarkerOptions()
                .position(newYork)
                .title("New York")
                .snippet("The Big Apple")
        )

        // Add click listener for markers
        googleMap?.setOnMarkerClickListener { marker ->
            Toast.makeText(requireContext(), marker.title, Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun toggleMapVisibility() {
        isMapVisible = !isMapVisible

        if (isMapVisible) {
            // Show map with animation
            binding.mapContainer.visibility = View.VISIBLE
            binding.mapContainer.alpha = 0f
            binding.mapContainer.animate().alpha(1f).duration = 300

            binding.buttonToggleMap.text = "Hide Map"
            binding.statusText.text = "Map is now VISIBLE"
        } else {
            // Hide map with animation
            binding.mapContainer.animate().alpha(0f).duration = 300.apply {
                setListener(object : android.animation.AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: android.animation.Animator?) {
                        binding.mapContainer.visibility = View.GONE
                    }
                })
            }

            binding.buttonToggleMap.text = "Show Map"
            binding.statusText.text = "Map is now HIDDEN"
        }

        Toast.makeText(
            requireContext(),
            if (isMapVisible) "Map Shown" else "Map Hidden",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}