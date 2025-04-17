package com.example.bundesligaapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bundesligaapp.activities.MainActivity
import com.example.bundesligaapp.activities.TeamActivity
import com.example.bundesligaapp.adapters.TeamsAdapter
import com.example.bundesligaapp.databinding.FragmentSearchBinding
import com.example.bundesligaapp.fragments.TeamsFragment.Companion.TEAM_BADGE
import com.example.bundesligaapp.fragments.TeamsFragment.Companion.TEAM_ID
import com.example.bundesligaapp.fragments.TeamsFragment.Companion.TEAM_NAME
import com.example.bundesligaapp.viewmodel.HomeViewModel
import com.example.bundesligaapp.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchRecyclerViewAdapter: TeamsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        prepareRecyclerView()
        searchViewModel.getTeamsForSearch()

        observeTeamsList()
        filterBySearch()

        onTeamClick()

    }

    private fun onTeamClick() {
        searchRecyclerViewAdapter.onItemClick = { team ->
            val intent = Intent(activity, TeamActivity::class.java)
            intent.putExtra(TEAM_ID, team.idTeam)
            intent.putExtra(TEAM_NAME, team.strTeam)
            intent.putExtra(TEAM_BADGE, team.strBadge)
            startActivity(intent)
        }
    }

    private fun filterBySearch() {
        binding.etSearchBox.doAfterTextChanged { query ->
            searchViewModel.filteredTeams(query.toString())
        }
    }

    private fun observeTeamsList() {
        searchViewModel.observeFilteredTeamsLiveData().observe(viewLifecycleOwner) { teams ->
            searchRecyclerViewAdapter.setTeamList(teams)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerViewAdapter = TeamsAdapter()
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(
                context, 2, GridLayoutManager.VERTICAL, false
            )
            adapter = searchRecyclerViewAdapter
        }
    }
}