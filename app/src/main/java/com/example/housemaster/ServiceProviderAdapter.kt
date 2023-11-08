package com.example.housemaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import org.w3c.dom.Text

class ServiceProviderAdapter(private val spList: ArrayList<ServiceProviderModel>) :
    RecyclerView.Adapter<ServiceProviderAdapter.ServiceProviderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceProviderViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.service_provider_item, parent, false)
        return ServiceProviderViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return spList.size
    }

    override fun onBindViewHolder(holder: ServiceProviderViewHolder, position: Int) {
        val currentItem = spList[position]
        holder.spName.text = currentItem.spName
        holder.spRatings.text = currentItem.spRatings
        holder.spImage.setImageResource(currentItem.spImage)

    }

    class ServiceProviderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spName: TextView = itemView.findViewById(R.id.spTitle)
        val spImage: ShapeableImageView = itemView.findViewById(R.id.spImage)
        val spRatings: TextView = itemView.findViewById(R.id.spRatings)
    }

}