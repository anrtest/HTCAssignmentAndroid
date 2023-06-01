package com.example.assignment.api.response.country

import android.R
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


@Parcelize
data class CountryResponseItem(
        val abbreviation: @RawValue String,
        val capital: @RawValue String,
        val currency: @RawValue String,
        val id: @RawValue Int,
        val media: @RawValue Media,
        val name: @RawValue String,
        val phone: @RawValue String,
        val population: @RawValue Int
): Parcelable{

    fun loadImage(view: ImageView) {
        Glide.with(view.context)
                .load(media.flag)
                .into(view)
    }
}