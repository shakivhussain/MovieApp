package com.shakib_mansoori.movieapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shakib_mansoori.movieapp.Model.MovieModel
import com.shakib_mansoori.movieapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var model: MovieModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = (intent.getSerializableExtra("object") as MovieModel?)!!

        init()
    }

    private fun init() {


        binding.apply {

            tvMovieTitle.text = model.title
            tvGenres.text = model.type
            tvRelease.text = model.released.toString()
            tvMovieDesc.text = model.synopsis

        }


    }

}