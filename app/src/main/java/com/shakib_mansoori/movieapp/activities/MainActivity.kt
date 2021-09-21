package com.shakib_mansoori.movieapp.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakib_mansoori.movieapp.Listener.Listener
import com.shakib_mansoori.movieapp.Model.MovieModel
import com.shakib_mansoori.movieapp.Model.OttModel
import com.shakib_mansoori.movieapp.R
import com.shakib_mansoori.movieapp.adapter.Adapter
import com.shakib_mansoori.movieapp.databinding.ActivityMainBinding
import com.shakib_mansoori.movieapp.utils.CheckInternet

class MainActivity : AppCompatActivity(), Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel
    private lateinit var dialog: Dialog
    private lateinit var checkInternet: CheckInternet
    private lateinit var adapter: Adapter
    private lateinit var arrayList: List<MovieModel>
    private lateinit var moviesList: List<OttModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.searchBar.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        })

        checkInternet = CheckInternet()

        setAdapter()

        dialogSet()
        init()

    }

    private fun dialogSet() {

        dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_progress)
        dialog.setCancelable(false)
        dialog.show()

    }

    private fun init() {
        if (!checkInternet.isConected(this)) {
            showCustomDialog()
        } else {
            viewModel = ViewModel()
            viewModel.fetchDataFromApi()


//            viewModel.searchMovie("the")
//
//            viewModel.getSearchMovieResults().observe(this) {
//
//                val arra = it.results
//
//                for (item in arra) {
////                    Log.d("jhfdjdhf", "init: Title ${item.type}" +
////                            "\nReleased ${item.released} \n Sys ${item.synopsis}")
//                }
//
//            }


//            viewModel.getUpcomingMovies().observe(this) {
//
//                arrayList = it.results
//
//                for (item in arrayList) {
//
//                    Log.d("jhfdjdhf", "init: Title ${item.synopsis}" +
//                            "\nReleased ${item.released} \n Sys ${item.synopsis}")
////                }
//
//            }


            viewModel.allOtts().observe(this) {
                if (it != null) {

                    moviesList = it
                    dialog.hide()

                    adapter.submitList(it)

                    val str = it.size
                    Log.d("jhfdjdhf", "init: " + str)
                }
            }
        }

    }

    private fun setAdapter() {

        binding.recyclerView
        adapter = Adapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.itemAnimator = null

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )


    }

    private fun showCustomDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Please connect to the internet to proceed further")
            .setCancelable(false)
            .setPositiveButton(
                "Connect"
            ) { dialog, which -> startActivity(Intent(Settings.ACTION_WIFI_SETTINGS)) }
            .setNegativeButton("Cancel") { dialog, which ->

                dialog.dismiss()
                init()
            }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onRestart() {
        super.onRestart()
        init()
    }

    override fun onItemClickListener(position: Int) {

        val details = moviesList[position]


        Toast.makeText(this, "Click On: " + details.label, Toast.LENGTH_LONG).show()

    }

}