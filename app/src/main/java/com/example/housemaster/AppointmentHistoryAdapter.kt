package com.example.housemaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class AppointmentHistoryAdapter(private val aptList: ArrayList<AppointmentListModel>) :
    RecyclerView.Adapter<AppointmentHistoryAdapter.AppointmentListViewHolder>() {

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_appointment_item, parent, false)
        return AppointmentListViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return aptList.size
    }

    override fun onBindViewHolder(holder: AppointmentListViewHolder, position: Int) {
        val currentItem = aptList[position]
        holder.aptSPName.text = currentItem.aptName
        holder.aptDate.text = currentItem.aptDate
        holder.aptCategory.text = currentItem.aptCategory
    }

    class AppointmentListViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val aptSPName: TextView = itemView.findViewById(R.id.alSPName)
        val aptDate: TextView = itemView.findViewById(R.id.alAptDate)
        val aptCategory: TextView = itemView.findViewById(R.id.alAptCategory)

        //for on click
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}