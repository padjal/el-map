package com.webdojobg.elmap.data.models

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot
import java.lang.Exception

class Station(
    val isOperational:Boolean,
    val address:String,
    val location:LatLng,
    val name:String,
    val number:Int
) {
    companion object{
        fun  DocumentSnapshot.toStation() : Station? {
            try {
                val address = getString("address")!!
                val name = getString("name")!!
                val number = get("number", Int::class.java)!!
                val location = getGeoPoint("location")!!
                val isOperational = getBoolean("is_operational")!!

                return Station(isOperational, address, LatLng(location.latitude, location.longitude), name, number)
            }catch (e : Exception){
                Log.e(TAG, "Error converting user profile", e)
                return null
            }
        }
        private const val TAG = "User"
    }
}