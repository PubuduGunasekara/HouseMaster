package com.example.housemaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class ServiceItemTypeAdapter(private val itemList: ArrayList<ServiceItemTypeModel>) :
    RecyclerView.Adapter<ServiceItemTypeAdapter.ItemTypeViewHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTypeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.service_item_types_item, parent, false)
        return ItemTypeViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemTypeViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemTitle.text = currentItem.typeTitle
        holder.itemPrice.text = currentItem.typePrice.toString()


    }


    class ItemTypeViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.siTypeTitle)
        val itemPrice: TextView = itemView.findViewById(R.id.siTypePrice)

        //for on click
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}