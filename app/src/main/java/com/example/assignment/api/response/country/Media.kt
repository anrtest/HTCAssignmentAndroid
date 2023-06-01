package com.example.assignment.api.response.country

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Media(
        val emblem: @RawValue String,
        val flag: @RawValue String,
        val orthographic: @RawValue String
): Parcelable