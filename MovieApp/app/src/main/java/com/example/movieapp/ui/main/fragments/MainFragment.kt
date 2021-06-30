package com.example.movieapp.ui.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.AppState
import com.example.movieapp.R
import com.example.movieapp.databinding.NewMainFragmentBinding
import com.example.movieapp.models.Movie
import com.example.movieapp.ui.main.adapter.MainFragmentAdapter
import com.example.movieapp.ui.main.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment() {

    private lateinit var binding: NewMainFragmentBinding

    private lateinit var viewModel: MainViewModel
    private var adapter : MainFragmentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewMainFragmentBinding.inflate(
            inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java) // то что подписывается на обновление данных в Вмодели
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })// Универсальная ссылка на фрагмент
        viewModel.getMovie()
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                mainLoadingLayout.visibility = View.GONE
                adapter = MainFragmentAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(movie: Movie) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(MovieCardFragment.BUNDLE_EXTRA, movie)
                            }
                            manager.beginTransaction()
                                    .replace(R.id.container, MovieCardFragment.newInstance(bundle))
                                    .addToBackStack("")
                                    .commit()
                        }
                    }
                }
                ).apply {
                    setMovie(appState.movieData)
                }
                mainRecyclerView.adapter = adapter
            }
            is AppState.Loading -> {
                binding.mainLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainLoadingLayout.visibility = View.GONE
                Snackbar
                        .make(binding.mainFragmentFAB, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.reload)) { viewModel.getMovie() }
                        .show()
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

        companion object {

        private const val api_key = "01141597fb9a845a9ce999e83b8db575"
        fun newInstance() = MainFragment()

    }
}


