package com.example.assignment.api.response.bylatlong

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize

data class ResponseByLatLong(
        val base: @RawValue String,
        val cod: @RawValue Int,
        val coord: @RawValue Coord,
        val dt: @RawValue Int,
        val id: @RawValue Int,
        val main: @RawValue Main,
        val name: @RawValue String,
        val timezone: @RawValue Int,
        val visibility: @RawValue Int,
        val weather: @RawValue List<Weather>
):Parcelable