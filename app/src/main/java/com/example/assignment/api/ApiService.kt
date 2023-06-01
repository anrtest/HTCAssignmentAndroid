package com.example.assignment.api

import com.example.assignment.BuildConfig
import com.example.assignment.api.response.bycity.ResponseByCityName
import com.example.assignment.api.response.bylatlong.ResponseByLatLong
import com.example.assignment.api.response.country.CountryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object{
        //const val BASE_URL = "https://samples.openweathermap.org/"
        //const val BASE_URL = "https://api.openweathermap.org/"
        const val BASE_URL = "https://api.sampleapis.com/"
        const val CLIENT_ID = BuildConfig.ACCESS_KEY
    }


    @GET("countries/countries")
    suspend fun getCountryList(
    ) : Response<CountryResponse>


    @GET("geo/1.0/direct")
    suspend fun searchByCityName(
        @Query("q") city: String,
        @Query("limit") limit: String = "10",
        @Query("appid") clientId: String = CLIENT_ID
    ) : Response<ResponseByCityName>


    @GET("https://api.openweathermap.org/data/2.5/weather")
    suspend fun searchByLatLng(
        @Query("lat") lat: String,
        @Query("lon") long: String,
        @Query("appid") clientId: String = CLIENT_ID
    ) : Response<ResponseByLatLong>

}