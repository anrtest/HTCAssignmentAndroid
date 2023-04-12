package com.example.assignment.api.response.bycity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ResponseByCityNameItem(
    val country: @RawValue String,
    val lat: @RawValue Double,
    val lon: @RawValue Double,
    val name: @RawValue String,
    val state: @RawValue String
): Parcelable