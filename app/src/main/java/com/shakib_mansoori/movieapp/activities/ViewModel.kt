package com.shakib_mansoori.movieapp.activities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakib_mansoori.movieapp.Model.OttModel
import com.shakib_mansoori.movieapp.repository.Repo
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    private val searchedMoviesResult: MutableLiveData<OttModel> = MutableLiveData()

    fun fetchDataFromApi() {
        viewModelScope.launch {
            Repo.fetchAllNews()
        }
    }


    fun allOtts(): LiveData<List<OttModel>> {
        return Repo.getOttList()
    }


    fun getUpcomingMovies(): LiveData<OttModel> {
        return Repo.getUpcomingMovies()
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            val movieResponse = Repo.searchMovie(query, 1)
            if (movieResponse.isSuccessful) {
                searchedMoviesResult.postValue(movieResponse.body())
            } else {
                Log.d(
                    "jhfdjdhf", "searchMovie: "
                            + movieResponse.message()
                            + movieResponse.code() + movieResponse.errorBody()
                )
            }
        }
    }

    fun getSearchMovieResults(): LiveData<OttModel> {
        return searchedMoviesResult
    }


}
