package com.example.housemaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ServiceItemTypeAdapter(private val itemList: ArrayList<ServiceItemTypeModel>) :
    RecyclerView.Adapter<ServiceItemTypeAdapter.ItemTypeViewHolder>() {
    //for on click
    private lateinit var mListener: onItemClickListener
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    //for on click
    interface onItemClickListener {
        fun onItemClick(position: Int, clickedView: View)
    }

    //for on click
    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTypeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.service_item_types_item, parent, false)
        return ItemTypeViewHolder(itemView, mListener, this)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemTypeViewHolder, position: Int) {
        val currentItem = itemList[position]


        holder.itemTitle.text = currentItem.typeTitle
        holder.itemPrice.text = currentItem.typePrice.toString()

        // Check if the current item is selected
        if (position == selectedItemPosition) {
            holder.itemViewGroup.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.misty_blue_dark
                )
            )
            holder.itemPrice.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.misty_blue
                )
            )
            holder.itemTitle.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.misty_blue
                )
            )

            holder.itemPriceCAD.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.misty_blue
                )
            )

        } else {
            holder.itemViewGroup.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.blue_grey
                )
            )

            holder.itemPrice.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
            holder.itemTitle.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )

            holder.itemPriceCAD.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
        }


    }


    class ItemTypeViewHolder(
        itemView: View,
        listener: onItemClickListener,
        adapter: ServiceItemTypeAdapter
    ) :
        RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.siTypeTitle)
        val itemPrice: TextView = itemView.findViewById(R.id.siTypePrice)
        val itemViewGroup: LinearLayout = itemView.findViewById(R.id.itemViewGroup)
        val itemPriceCAD: TextView = itemView.findViewById(R.id.siTypePriceCAD)

        //for on click
        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    // Notify the listener about the item click
                    listener.onItemClick(adapterPosition, itemView)

                    // Update the selected item position
                    adapter.selectedItemPosition = adapterPosition

                    // Notify the adapter about the change
                    adapter.notifyDataSetChanged()
                }
            }
        }

        fun bind(item: ServiceItemTypeModel, isSelected: Boolean) {
            itemTitle.text = item.typeTitle
            itemPrice.text = item.typePrice.toString()

            // Update the background color based on selection
            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (isSelected) R.color.grey else android.R.color.transparent
                )
            )
        }
    }

}