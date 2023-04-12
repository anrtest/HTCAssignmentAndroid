package com.example.assignment.api.response.bylatlong

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize

data class Weather(
        val description: @RawValue String,
        val icon: @RawValue String,
        val id: @RawValue Int,
        val main: @RawValue String
): Parcelable