package com.example.bundesligaapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bundesligaapp.activities.TeamActivity
import com.example.bundesligaapp.adapters.TeamsAdapter
import com.example.bundesligaapp.databinding.FragmentTeamsBinding
import com.example.bundesligaapp.viewmodel.HomeViewModel
import com.example.bundesligaapp.viewmodel.TeamsViewModel

class TeamsFragment : Fragment() {

    private lateinit var binding: FragmentTeamsBinding
    private lateinit var teamsAdapter: TeamsAdapter
    private lateinit var teamsViewModel: TeamsViewModel

    companion object {
        const val TEAM_ID = "com.example.bundesligaapp.fragments.idTeam"
        const val TEAM_NAME = "com.example.bundesligaapp.fragments.nameTeam"
        const val TEAM_BADGE = "com.example.bundesligaapp.fragments.badgeTeam"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teamsViewModel = ViewModelProvider(this)[TeamsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        teamsViewModel.getTeams()
        observeTeams()

        onTeamClick()
    }

    private fun prepareRecyclerView() {
        teamsAdapter = TeamsAdapter()
        binding.rvTeams.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = teamsAdapter
        }
    }

    private fun observeTeams() {
        teamsViewModel.observeTeamsLiveData().observe(viewLifecycleOwner, Observer { teams ->
            Log.d("TeamsFragment", "Teams list size: ${teams.size}")
            teamsAdapter.setTeamList(teams)

        })
    }

    private fun onTeamClick() {
        teamsAdapter.onItemClick = { team ->
            val intent = Intent(activity, TeamActivity::class.java)
            intent.putExtra(TEAM_ID, team.idTeam)
            intent.putExtra(TEAM_NAME, team.strTeam)
            intent.putExtra(TEAM_BADGE, team.strBadge)
            startActivity(intent)
        }
    }
}