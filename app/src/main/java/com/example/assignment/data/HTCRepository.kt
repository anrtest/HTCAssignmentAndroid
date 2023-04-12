package com.example.assignment.data

import androidx.lifecycle.MutableLiveData
import com.example.assignment.api.ApiService
import com.example.assignment.api.response.bycity.ResponseByCityName
import com.example.assignment.api.response.bylatlong.ResponseByLatLong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HTCRepository @Inject constructor(private val apiService: ApiService) {

    fun hitSearchByCity(
        cityName: String,
        liveData: MutableLiveData<ResponseByCityName>,
        viewModelScope: CoroutineScope
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val result = apiService.searchByCityName(cityName)
            if (result.isSuccessful) {
                liveData.postValue(result.body())
            }
        }
    }

    fun hitSearchByLatLong(
        lat: String,
        long: String,
        liveData: MutableLiveData<ResponseByLatLong>,
        viewModelScope: CoroutineScope
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val result = apiService.searchByLatLng(lat, long)
            if (result.isSuccessful) {
                liveData.postValue(result.body())
            }
        }
    }
}