package com.shakib_mansoori.movieapp.Model

import java.io.Serializable


data class MovieModel(
    val genres: List<String>,
    val released: Long,
    val imdbrating: String,
    val title: String,
    val type: String,
    val synopsis: String,
    val imageurl: List<String>


) : Serializable

