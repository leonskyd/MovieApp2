package com.example.movieapp.ui.main.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.AppState
import com.example.movieapp.models.Repository

class MovieCardViewModel (
        private val repository: Repository
        ) : ViewModel(), LifecycleObserver{

            val liveDataToRead: MutableLiveData<AppState> = MutableLiveData()


        fun LoadData(id: Int?) {
            liveDataToRead.value = AppState.Loading
            Thread{
                val data = repository.showMovieFromServer(id)
                liveDataToRead.postValue(AppState.Success(listOf(data)))
            }.start()

        }

}