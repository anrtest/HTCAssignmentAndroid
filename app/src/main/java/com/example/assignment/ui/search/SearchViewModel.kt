package com.example.assignment.ui.search

import android.location.Location
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.assignment.api.response.bycity.ResponseByCityName
import com.example.assignment.api.response.bylatlong.ResponseByLatLong
import com.example.assignment.data.HTCRepository

class SearchViewModel @ViewModelInject constructor(
        private val repository: HTCRepository,
        @Assisted state : SavedStateHandle
) : ViewModel() {

    var searchByCityResponse: MutableLiveData<ResponseByCityName> = MutableLiveData<ResponseByCityName>()
    var searchByLatLong: MutableLiveData<ResponseByLatLong> = MutableLiveData<ResponseByLatLong>()


    fun callWeatherApi(cityName: String){
        repository.hitSearchByCity(cityName, searchByCityResponse, viewModelScope)
    }

    fun callWeatherApiByLocation(location: Location){
        repository.hitSearchByLatLong(location.latitude.toString(), location.longitude.toString(), searchByLatLong, viewModelScope)
    }


    fun callWeatherApiByLocation(latitude: String, longitude: String){
        repository.hitSearchByLatLong(latitude, longitude, searchByLatLong, viewModelScope)
    }

}