package com.webdojobg.elmap.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.webdojobg.elmap.data.datasources.StationsFirebaseDataSource
import com.webdojobg.elmap.data.models.Station
import com.webdojobg.elmap.data.models.Station.Companion.toStation

/**
 * A repository class for stations objects.
 *
 * @property stationsFirebaseDataSource NOT_IMPLEMENTED_YET! A Firebase datasource for retrieving stations from the Firebase
 * Firestore realtime noSQL database.
 * @property firebaseInstance The instance of the firebase realtime database needed for getting the stations.
 */
class StationsRepository(
    private val stationsFirebaseDataSource: StationsFirebaseDataSource = StationsFirebaseDataSource(),
    private val firebaseInstance: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    /**
     * Get all stations from the database. The return type of the function is LiveData in order to support
     * data flow from the data to the ui layer.
     *
     * @return A LiveData object, which references a list of stations.
     */
    fun getStations(): LiveData<List<Station>> {
        val stations = MutableLiveData<List<Station>>()

        firebaseInstance.collection("stations")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    val listOfStations = ArrayList<Station>()

                    for (document in snapshot.documents) {
                        listOfStations.add(document.toStation()!!)
                        Log.i(TAG, document.toString())
                    }

                    stations.postValue(listOfStations)
                }
            }

        return stations
    }
}