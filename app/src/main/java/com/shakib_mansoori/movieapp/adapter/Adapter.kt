package com.shakib_mansoori.movieapp.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.shakib_mansoori.movieapp.Listener.Listener
import com.shakib_mansoori.movieapp.Model.OttModel
import com.shakib_mansoori.movieapp.R
import com.shakib_mansoori.movieapp.databinding.ListItemBinding


class Adapter(private val listener: Listener) : RecyclerView.Adapter<Adapter.TempHolder>() {

    private var list = emptyList<OttModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: TempHolder, position: Int) {
        val news = list[position]

        holder.binding.headlineText.text = news.label

        Log.d("jhfdjdhf", "onBindViewHolder: " + list.size)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return TempHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(list: List<OttModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class TempHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view)

        init {
            view.setOnClickListener {
                listener.onItemClickListener(adapterPosition)
            }
        }

    }
}
