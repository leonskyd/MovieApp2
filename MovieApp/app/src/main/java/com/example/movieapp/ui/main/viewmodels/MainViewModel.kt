package com.example.movieapp.ui.main.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.AppState
import com.example.movieapp.models.Repository
import com.example.movieapp.models.RepositoryAct

class MainViewModel(
        val liveDataToRead: MutableLiveData<AppState> = MutableLiveData(),
        private val repository: Repository = RepositoryAct()
) : ViewModel(), LifecycleObserver{

    /*fun getData(): LiveData<AppState> {
        getDataFromLocalSource() // потом произойдет обновление данных
        return liveDataToRead // сначала мы получим подписку и на нее подпишемся
    }*/

    fun getLiveData()=liveDataToRead

    fun getMovie()= getDataFromLocalSource()

    private fun getDataFromLocalSource() { // а потом сработает обновление данных
        liveDataToRead.value = AppState.Loading // эмуляция загрузки
        Thread {
            Thread.sleep(1000)
           liveDataToRead.postValue( //synchronization with UI thread
                AppState.Success(
                    repository.showMovieList()))
           // liveDataToRead.postValue(AppState.Success(Any()))

            }.start()
    }

    fun LoadData(id: Int?) {
        liveDataToRead.value = AppState.Loading
        Thread{
            val data = repository.showMovieFromServer(id)
            liveDataToRead.postValue(AppState.Success(listOf(data)))
        }.start()

    }


}