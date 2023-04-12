package com.example.assignment.di

import android.content.Context
import android.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPreference @Inject constructor(@ApplicationContext context : Context){
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val LAT = "Lat"
    val LNG = "Lon"

    fun getLat(): String? {
        return prefs.getString(LAT, "")!!
    }
    fun setLat(query: String) {
        prefs.edit().putString(LAT, query).apply()
    }
    fun getLng(): String? {
        return prefs.getString(LNG, "")!!
    }
    fun setLng(query: String) {
        prefs.edit().putString(LNG, query).apply()
    }


}