package com.example.movieapp.models

import com.example.movieapp.ui.main.MovieLoader

class RepositoryAct: Repository{

    override fun showMovieFromServer(id: Int?): Movie {
        val info = MovieLoader.loadMovieFromWeb(id)
        return Movie(
                movie_id = info?.id,
                title = info?.original_title,
                release = info?.release_date,
                overview = info?.overview,
                rank = info?.vote_average
        )
    }

    override fun showMovieList() = getMovieList()
}