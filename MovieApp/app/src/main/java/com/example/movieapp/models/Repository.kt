package com.example.movieapp.models

interface Repository {

    fun showMovieFromServer(id: Int?): Movie
    fun showMovieList(): List<Movie>
}