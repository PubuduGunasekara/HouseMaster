package com.example.housemaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class ServiceCategoryAdapter(private val categoryList: ArrayList<ServiceCategoryModel>) :
    RecyclerView.Adapter<ServiceCategoryAdapter.CategoryViewHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rouded_category_item, parent, false)
        return CategoryViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.categoryTitle.text = currentItem.categoryName
        holder.categoryImage.setImageResource(currentItem.categoryImage)


    }


    class CategoryViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val categoryTitle: TextView = itemView.findViewById(R.id.categoryTitle)
        val categoryImage: ShapeableImageView = itemView.findViewById(R.id.categoryImage)

        //for on click
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}