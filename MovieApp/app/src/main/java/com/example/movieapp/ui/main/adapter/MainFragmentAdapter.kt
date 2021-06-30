package com.example.movieapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.FragmentDetailBinding
import com.example.movieapp.models.Movie
import com.example.movieapp.ui.main.fragments.MainFragment

class MainFragmentAdapter (
        private var itemClickListener:
        MainFragment.OnItemViewClickListener?) :
        RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private lateinit var binding: FragmentDetailBinding
    private var movieData: List<Movie> = listOf()

    fun setMovie(data: List<Movie>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
    ): MainViewHolder {
        binding = FragmentDetailBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(
            holder: MainFragmentAdapter.MainViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) = with(binding){
            mainRecyclerItemView.text = movie.title
            root.setOnClickListener {
                //переход
                itemClickListener?.onItemViewClick(movie)
            }
        }
    }
}
