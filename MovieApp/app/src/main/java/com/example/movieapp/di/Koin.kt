package com.example.movieapp.di

import com.example.movieapp.models.Repository
import com.example.movieapp.models.RepositoryAct
import com.example.movieapp.ui.main.viewmodels.MainViewModel
import com.example.movieapp.ui.main.viewmodels.MovieCardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { RepositoryAct() }

    viewModel { MovieCardViewModel(get()) }

}