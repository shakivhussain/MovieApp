package com.shakib_mansoori.movieapp.Model

data class OttModel(
    val label: String,
    val value: String,
    val short: String,
    val results: List<MovieModel>,
)

