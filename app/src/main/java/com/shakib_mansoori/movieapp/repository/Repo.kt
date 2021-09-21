package com.shakib_mansoori.movieapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shakib_mansoori.movieapp.Model.OttModel
import com.shakib_mansoori.movieapp.utils.Constant
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repo {


    private var ottList: MutableLiveData<List<OttModel>> = MutableLiveData()
    private var upComingMovieList: MutableLiveData<OttModel> = MutableLiveData()

    private val api = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)


    suspend fun fetchAllNews() {


        val newsResponse = api.getOTTList("IN")
        if (newsResponse.isSuccessful) {
            val list = newsResponse.body()
            ottList.postValue(list)
        }

        val upcomingMovieResponse = api.getUpcomingMovies("IN", 1)
        if (upcomingMovieResponse.isSuccessful) {
            Log.d("jhfdjdhf", "fetchAllNews: " + upcomingMovieResponse.message())
            val list = upcomingMovieResponse.body()
            upComingMovieList.postValue(list)
        } else {
            val str = "Msg : " + upcomingMovieResponse.message() +
                    "\n Code: " + upcomingMovieResponse.code()
            Log.d("jhfdjdhf", "fetchAllNews: " + str)
        }


    }


    fun getOttList(): LiveData<List<OttModel>> {
        return ottList
    }

    fun getUpcomingMovies(): LiveData<OttModel> {
        return upComingMovieList
    }

    suspend fun searchMovie(query: String, page: Int): Response<OttModel> {
        return api.searchMovies(query, page)
    }

}