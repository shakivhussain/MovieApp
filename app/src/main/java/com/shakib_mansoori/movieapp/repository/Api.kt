package com.shakib_mansoori.movieapp.repository

import com.shakib_mansoori.movieapp.Model.OttModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {

    // https://newsapi.org/v2/everything?q=apple&from=2021-09-08&to=2021-09-08&sortBy=popularity&apiKey=6837b1fe03d242d4ba22ff7a91502252
    // category=health&language=en

    @Headers(
        "x-rapidapi-host: ott-details.p.rapidapi.com",
        "x-rapidapi-key: 77237a7575msh83678d1c982b057p186404jsnd0e3b7462cda"
    )
    @GET("getPlatforms")
    suspend fun getOTTList(
        @Query("region") q: String,
    ): Response<List<OttModel>>


    @Headers(
        "x-rapidapi-host: ott-details.p.rapidapi.com",
        "x-rapidapi-key: 77237a7575msh83678d1c982b057p186404jsnd0e3b7462cda"
    )
    @GET("search")
    suspend fun searchMovies(
        @Query("title") q: String,
        @Query("page") page: Int
    ): Response<OttModel>


    @Headers(
        "x-rapidapi-host: ott-details.p.rapidapi.com",
        "x-rapidapi-key: 77237a7575msh83678d1c982b057p186404jsnd0e3b7462cda"
    )
    @GET("getnew")
    suspend fun getUpcomingMovies(
        @Query("region") region: String,
        @Query("page") page: Int
    ): Response<OttModel>

}