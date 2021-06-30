package com.example.movieapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val movie_id: Int?,
    val title: String?,
    val release: String?,
    val overview: String?,
    val rank: Double?
) : Parcelable


fun getMovieList(): List<Movie> {
    return listOf(
        Movie(1,"Pulp Fiction", "1995", "classic!", 8.9),
        Movie(2,"Shawshank Redemption", "1992","best ever", 9.2),
        Movie(3,"City of god", "2008","hard to watch",8.7),
        Movie(4,"Titanic",  "1998","you will cry",9.0),
        Movie(550,"Fight Club", "1999","man´s life",8.5),
    )
}