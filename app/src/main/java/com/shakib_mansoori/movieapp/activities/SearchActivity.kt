package com.shakib_mansoori.movieapp.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakib_mansoori.movieapp.Listener.Listener
import com.shakib_mansoori.movieapp.Model.MovieModel
import com.shakib_mansoori.movieapp.R
import com.shakib_mansoori.movieapp.adapter.SearchAdapter
import com.shakib_mansoori.movieapp.databinding.ActivitySearchBinding
import com.shakib_mansoori.movieapp.utils.CheckInternet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity(), Listener {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: ViewModel
    private lateinit var checkInternet: CheckInternet
    private lateinit var adapter: SearchAdapter
    private lateinit var dialog: Dialog
    private lateinit var list: List<MovieModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkInternet = CheckInternet()
        viewModel = ViewModel()

        binding.btnBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        init()

        setAdapter()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT

        this.showKeyboard(binding.searchBar)
    }


    private fun dialogSet() {

        dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_progress)
        dialog.setCancelable(false)
        dialog.show()

    }


    fun Context.showKeyboard(editText: SearchView) {

        editText.performClick()
        editText.requestFocus()

        viewModel.viewModelScope.launch {
            launch {
                delay(200L)
                val inputMethodManager: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.toggleSoftInputFromWindow(
                    editText.applicationWindowToken,
                    InputMethodManager.SHOW_IMPLICIT, 0
                )
            }
        }

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

    private fun init() {
        if (!checkInternet.isConected(this)) {
            showCustomDialog()
            Toast.makeText(this, "Please Connect Internet", Toast.LENGTH_SHORT).show()
        } else {


            searchMovieList("Endgame")
            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {

                    if (!checkInternet.isConected(applicationContext)) {
                        showCustomDialog()

                    } else {
                        if (query != null) {
                            Toast.makeText(this@SearchActivity, query, Toast.LENGTH_SHORT).show()
                            binding.recyclerView.scrollToPosition(0)
                            searchMovieList(query)
                            binding.searchBar.clearFocus()
                            binding.searchBar.imeOptions
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })


            viewModel.getSearchMovieResults().observe(this) {
                if (it != null) {

                    list = it.results
                    adapter.submitList(list)

                }
            }
        }

    }

    private fun searchMovieList(query: String) {

        viewModel.searchMovie(query)
    }

    private fun setAdapter() {

        binding.recyclerView
        adapter = SearchAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.itemAnimator = null

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )


    }


    override fun onRestart() {
        super.onRestart()
        init()
    }

    override fun onItemClickListener(position: Int) {

        val moview = list[position]

        Log.d("clickOn", "onItemClickListener: Click On:  " + moview.title)
    }

}

