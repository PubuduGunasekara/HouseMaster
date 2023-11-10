package com.example.housemaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class SearchCategoryAdapter(private val movieList: ArrayList<ServiceCategoryModel>) :
    RecyclerView.Adapter<SearchCategoryAdapter.MovieViewHolder>() {

    //for on click
    private lateinit var mListener: onItemClickListener

    //for on click
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    //for on click
    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_category_list_item, parent, false)
        return MovieViewHolder(itemView,mListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movieList[position]
        holder.movieTitle.text = currentItem.categoryName
        holder.movieImage.setImageResource(currentItem.categoryImage)
    }

    class MovieViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        val movieImage: ShapeableImageView = itemView.findViewById(R.id.movieImage)


        //for on click
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}