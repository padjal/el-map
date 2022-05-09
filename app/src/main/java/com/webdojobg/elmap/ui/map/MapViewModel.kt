package com.webdojobg.elmap.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.webdojobg.elmap.data.datasources.StationsFirebaseDataSource
import com.webdojobg.elmap.data.models.Station
import com.webdojobg.elmap.data.repositories.StationsRepository

/**
 * Used for holding the data of the map fragment.
 *
 */
class MapViewModel : ViewModel() {
    private val stationsRepository = StationsRepository()
    private val stationsDataSource = StationsFirebaseDataSource()
    val stations: LiveData<List<Station>> = stationsRepository.getStations()
    var selectedStation = MutableLiveData<Station>()
    var isStationInfoVisible = MutableLiveData<Boolean>(false)

    /**
     * Select station based on the marker title the use has selected in the map fragment.
     * Used to change the selectedStation and visualize information about the selected station.
     *
     * @param name The title of the marker which the user selected. Used to match a station by its
     * name in the stations collection.
     */
    fun selectStation(name: String) {
        val foundStation = stations.value?.find { station -> station.name == name }
        selectedStation.value = foundStation!!
        isStationInfoVisible.value = true
    }
}