package com.webdojobg.elmap.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.webdojobg.elmap.data.datasources.StationsFirebaseDataSource
import com.webdojobg.elmap.data.models.Station
import com.webdojobg.elmap.data.models.Station.Companion.toStation
import kotlinx.coroutines.runBlocking

class StationsRepository(
    private val stationsFirebaseDataSource: StationsFirebaseDataSource = StationsFirebaseDataSource(),
    private val firebase : FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    fun getStations() : LiveData<List<Station>> {
        val stations = MutableLiveData<List<Station>>()

        firebase.collection("stations")
            .get()
            .addOnSuccessListener {
                snapshot ->
                if(snapshot != null){
                    val listOfStations = ArrayList<Station>()

                    for (document in snapshot.documents){
                        listOfStations.add(document.toStation()!!)
                        Log.i(TAG, document.toString())
                    }

                    stations.postValue(listOfStations)
                }
            }

        return stations
    }
}