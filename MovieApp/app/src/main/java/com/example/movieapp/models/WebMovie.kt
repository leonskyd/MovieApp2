package com.example.movieapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class WebMovie(
        val id: Int?,
        val original_title: String?,
        val release_date: String?,
        val overview: String?,
        val vote_average: Double?
) //: Parcelable

