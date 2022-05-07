package com.webdojobg.elmap.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.webdojobg.elmap.data.datasources.StationsFirebaseDataSource
import com.webdojobg.elmap.data.models.Station
import com.webdojobg.elmap.data.repositories.StationsRepository

/**
 * Used for holding the data of the map fragment.
 */
class MapViewModel : ViewModel() {
    private val stationsRepository = StationsRepository()
    private val stationsDataSource = StationsFirebaseDataSource()
    val stations : LiveData<List<Station>> = stationsRepository.getStations()

    private fun loadStations() {
        /*// Do an asynchronous operation to fetch users.
        val sts = ArrayList<Station>()
        sts.add(Station(true, "Test address", LatLng(4.0, 3.6), "First station", 1))
        sts.add(Station(true, "Test address 2", LatLng(4.0, 3.6), "First station 2", 1))
        sts.add(Station(true, "Test address 3", LatLng(4.0, 3.6), "First station 3", 1))

        stations.postValue(sts)*/
    }
}