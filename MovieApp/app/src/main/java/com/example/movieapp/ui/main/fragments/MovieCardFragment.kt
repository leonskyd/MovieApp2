package com.example.movieapp.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.AppState
import com.example.movieapp.databinding.MovieCardFragmentBinding
import com.example.movieapp.models.Movie
import com.example.movieapp.ui.main.viewmodels.MainViewModel
import com.example.movieapp.ui.main.viewmodels.MovieCardViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieCardFragment: Fragment() {

    private lateinit var binding: MovieCardFragmentBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var movieBundle: Movie

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        Log.d("Denis", "NewFragment")
        binding = MovieCardFragmentBinding.inflate(
                inflater, container, false)
        Log.d("Denis", "NewFragment1")
        return binding.root
        Log.d("Denis", "NewFragment2")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val movie = arguments?.getParcelable<Movie>(BUNDLE_EXTRA)
        movie?.let {
            setCardFromWeb(it)
            //setCard(it)
        }
    }

    private fun setCardFromWeb(it: Movie) = with(binding) {
        title.text = it.title
        viewModel.liveDataToRead.observe(viewLifecycleOwner, { appState ->
            when (appState) {
                is AppState.Error -> {
                    loadingLayout.visibility = View.GONE
                }
                AppState.Loading -> loadingLayout.visibility = View.VISIBLE
                is AppState.Success -> {
                        loadingLayout.visibility = View.GONE
                        year.text = appState.movieData[0].release
                        overView.text = appState.movieData[0].overview
                        rank.text = appState.movieData[0].rank.toString()
                }
            }
        })
        viewModel.LoadData(it.movie_id)
    }


    private fun setCard(movieCard: Movie) = with(binding) {
        title.text = movieCard.title
        overView.text = movieCard.overview
        year.text = movieCard.release
        rank.text = movieCard.rank.toString()
    }


    companion object {
        const val BUNDLE_EXTRA = "movie"
        private const val api_key = "01141597fb9a845a9ce999e83b8db575"

        fun newInstance(bundle: Bundle):MovieCardFragment {
            val fragment = MovieCardFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
