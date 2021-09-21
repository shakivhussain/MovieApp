package com.shakib_mansoori.movieapp.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.shakib_mansoori.movieapp.Listener.Listener
import com.shakib_mansoori.movieapp.Model.MovieModel
import com.shakib_mansoori.movieapp.R
import com.shakib_mansoori.movieapp.activities.DetailActivity
import com.shakib_mansoori.movieapp.databinding.ListItemBinding


class SearchAdapter(private val listener: Listener) :
    RecyclerView.Adapter<SearchAdapter.TempHolder>() {

    private var list = emptyList<MovieModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: TempHolder, position: Int) {
        val news = list[position]

        holder.binding.headlineText.text = news.title
        holder.binding.type.text = news.type

        holder.itemView.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(
                holder.itemView.context,
                DetailActivity::class.java
            )
            intent.putExtra("object", list[position])
            holder.itemView.context.startActivity(intent)
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return TempHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(list: List<MovieModel>) {
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
