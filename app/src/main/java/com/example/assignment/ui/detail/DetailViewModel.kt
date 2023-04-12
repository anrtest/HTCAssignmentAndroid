package com.example.assignment.ui.detail

import android.location.Location
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.assignment.api.response.bycity.ResponseByCityName
import com.example.assignment.api.response.bylatlong.ResponseByLatLong
import com.example.assignment.data.HTCRepository

class DetailViewModel @ViewModelInject constructor(
        private val repository: HTCRepository,
        @Assisted state : SavedStateHandle
) : ViewModel() {

    var searchByLatLong: MutableLiveData<ResponseByLatLong> = MutableLiveData<ResponseByLatLong>()

    fun callWeatherApiByLocation(latitude: Double, longitude: Double){
        repository.hitSearchByLatLong(latitude.toString(), longitude.toString(), searchByLatLong, viewModelScope)
    }

}