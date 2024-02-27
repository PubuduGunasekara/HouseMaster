package com.example.housemaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class BookAppointmentTimeSlotAdapter(private val itemList: ArrayList<BookAppointmentTimeSlotModel>) :
    RecyclerView.Adapter<BookAppointmentTimeSlotAdapter.TimeSlotViewHolder>() {
    //for on click
    private lateinit var mListener: onItemClickListener
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    //for on click
    interface onItemClickListener {
        fun onItemClick(position: Int, itemView: View)
    }

    fun updateItems(newItemList: List<BookAppointmentTimeSlotModel>) {
        itemList.clear()
        itemList.addAll(newItemList)
        notifyDataSetChanged()
    }

    //for on click
    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_appointment_time_slot_item, parent, false)
        return TimeSlotViewHolder(itemView, mListener, this)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        val currentItem = itemList[position]
        //holder.itemTitle.text = currentItem.timeSlotId
        holder.timeSlotTitle.text = currentItem.timeSlotTitle

        // Check if the current item is selected
        if (position == selectedItemPosition) {
            holder.timeSLotGroup.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.misty_blue_dark
                )
            )
            holder.timeSlotTitle.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.misty_blue
                )
            )

        } else {
            holder.timeSLotGroup.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.blue_grey
                )
            )
            holder.timeSlotTitle.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
        }


    }



    class TimeSlotViewHolder(
        itemView: View,
        listener: onItemClickListener,
        adapter: BookAppointmentTimeSlotAdapter
    ) :
        RecyclerView.ViewHolder(itemView) {
        val timeSlotTitle: TextView = itemView.findViewById(R.id.timeSlotTitle)
        val timeSLotGroup: LinearLayout = itemView.findViewById(R.id.timeSlotGroup)

        //for on click
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

        fun bind(item: BookAppointmentTimeSlotModel, isSelected: Boolean) {
            timeSlotTitle.text = item.timeSlotTitle
        }
    }

}