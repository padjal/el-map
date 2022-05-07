package com.webdojobg.elmap.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.webdojobg.elmap.data.datasources.StationsFirebaseDataSource
import com.webdojobg.elmap.data.models.Station
import com.webdojobg.elmap.data.repositories.StationsRepository
import java.util.function.BinaryOperator

/**
 * Used for holding the data of the map fragment.
 */
class MapViewModel : ViewModel() {
    private val stationsRepository = StationsRepository()
    private val stationsDataSource = StationsFirebaseDataSource()
    val stations : LiveData<List<Station>> = stationsRepository.getStations()
    var selectedStation = MutableLiveData<Station>()
    var isStationInfoVisible = MutableLiveData<Boolean>(false)

    fun selectStation(name: String){
        val foundStation = stations.value?.find { station -> station.name == name }
        selectedStation.value = foundStation!!
        isStationInfoVisible.value = true
    }
}