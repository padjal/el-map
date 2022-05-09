package com.webdojobg.elmap.data.models

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot

/**
 * A model class representing a single charging station mapped to the database entries.
 *
 * @property isOperational True if station is currently available for regular users.
 * @property address The physical address of the station.
 * @property location The geographical location of the station, represented in degrees.
 * @property name A user-friendly name for the station, used for easy identification.
 * @property number The serial number of the station used for internal configurations and maintenance.
 * @property imageUrl The relative url address of the image of the station.
 */
class Station(
    val isOperational: Boolean,
    val address: String,
    val location: LatLng,
    val name: String,
    val number: Int,
    val imageUrl: String?
) {
    companion object {
        /**
         * Convert a result from a Firebase query to a station.
         *
         * @return The parsed station from the db request.
         */
        fun DocumentSnapshot.toStation(): Station? {
            try {
                val address = getString("address")!!
                val name = getString("name")!!
                val number = get("number", Int::class.java)!!
                val location = getGeoPoint("location")!!
                val isOperational = getBoolean("is_operational")!!
                val imageUrl = getString("image_url")

                return Station(
                    isOperational,
                    address,
                    LatLng(location.latitude, location.longitude),
                    name,
                    number,
                    imageUrl
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error converting user profile", e)
                return null
            }
        }

        private const val TAG = "User"
    }
}