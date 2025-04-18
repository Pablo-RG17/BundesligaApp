package com.example.bundesligaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bundesligaapp.R
import com.example.bundesligaapp.databinding.VenueItemBinding
import com.example.bundesligaapp.pojo.Venue

class VenuesAdapter() : RecyclerView.Adapter<VenuesAdapter.VenuesViewHolder>() {

    private var venuesList = ArrayList<Venue>()
    lateinit var onItemClick: (Venue) -> Unit

    fun setVenueList(venueList: List<Venue>) {
        this.venuesList = venueList as ArrayList<Venue>
        notifyDataSetChanged()
    }

    class VenuesViewHolder(val binding: VenueItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenuesViewHolder {
        return VenuesViewHolder(
            VenueItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: VenuesViewHolder, position: Int) {

        val venue = venuesList[position]

        Glide.with(holder.itemView)
            .load(venue.strThumb)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.binding.ivVenueImage)

        holder.binding.tvVenueName.text = venue.strVenue

        holder.itemView.setOnClickListener {
            onItemClick(venue)
        }
    }

    override fun getItemCount() = venuesList.size
}