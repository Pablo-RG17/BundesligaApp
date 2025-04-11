package com.example.bundesligaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bundesligaapp.R
import com.example.bundesligaapp.databinding.TeamItemBinding
import com.example.bundesligaapp.pojo.Team

class TeamsAdapter() : RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    private var teamsList = ArrayList<Team>()
    lateinit var onItemClick: (Team) -> Unit

    fun setTeamList(teamList: List<Team>) {
        this.teamsList = teamList as ArrayList<Team>
        notifyDataSetChanged()
    }

    class TeamsViewHolder(val binding: TeamItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(
            TeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {

        val team = teamsList[position]

        Glide.with(holder.itemView)
            .load(team.strBadge)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.binding.ivTeamLogo)

        holder.binding.tvTeamName.text = team.strTeam

        holder.itemView.setOnClickListener {
            onItemClick(team)
        }
    }

    override fun getItemCount() = teamsList.size
}