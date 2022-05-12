package com.webdojobg.elmap.data.datasources

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.webdojobg.elmap.data.models.Station
import com.webdojobg.elmap.data.models.Station.Companion.toStation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@Deprecated("Class currently not used. Should be later implemented using Kotlin Flows.")
class StationsFirebaseDataSource() {
    val database = FirebaseFirestore.getInstance()

    fun getStations() : LiveData<List<Station>> {
        val result = MutableLiveData<List<Station>>()

        var stations = ArrayList<Station>()
        database.collection("stations")
            .addSnapshotListener { snapshot, error ->
                if(error != null){
                    Log.w(TAG, "Listen failed", error)
                    return@addSnapshotListener
                }
                // if we are here, we did not encounter an exception
                if(snapshot!=null){
                    val allStations = ArrayList<Station>()

                    snapshot.documents.forEach {
                        val station = it.toStation()
                        if(station != null){
                            allStations.add(station)
                        }
                    }

                    val test = allStations[0]

                    Log.i(TAG, "HEY")
                }
            }

        result.value = stations
        return result
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getStations2() : Flow<List<Station>> {
        return callbackFlow {
            database.collection("stations")
                .addSnapshotListener { snapshot, error ->
                    if(error != null){
                        Log.w(TAG, "Listen failed", error)
                        return@addSnapshotListener
                    }
                    // if we are here, we did not encounter an exception
                    if(snapshot!=null){
                        val map = snapshot.documents.mapNotNull {
                            it.toStation()
                        }

                        trySend(map)
                        /*val allStations = ArrayList<Station>()

                        snapshot.documents.forEach {
                            val station = it.toStation()
                            if(station != null){
                                allStations.add(station)
                            }
                        }

                        val test = allStations[0]

                        Log.i(TAG, "HEY")*/
                    }
                }
        }
    }
}
