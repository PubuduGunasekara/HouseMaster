package com.example.housemaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class BookAppointmentTimeSlotAdapter(private val itemList: ArrayList<BookAppointmentTimeSlotModel>) :
    RecyclerView.Adapter<BookAppointmentTimeSlotAdapter.TimeSlotViewHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_appointment_time_slot_item, parent, false)
        return TimeSlotViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        val currentItem = itemList[position]
        //holder.itemTitle.text = currentItem.timeSlotId
        holder.timeSlotTitle.text = currentItem.timeSlotTitle


    }


    class TimeSlotViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val timeSlotTitle: TextView = itemView.findViewById(R.id.timeSlotTitle)

        //for on click
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}