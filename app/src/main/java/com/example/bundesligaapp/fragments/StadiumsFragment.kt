package com.example.bundesligaapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bundesligaapp.adapters.VenuesAdapter
import com.example.bundesligaapp.databinding.FragmentStadiumsBinding
import com.example.bundesligaapp.viewmodel.TeamsViewModel
import com.example.bundesligaapp.viewmodel.VenuesViewModel

class StadiumsFragment : Fragment() {

    private lateinit var binding: FragmentStadiumsBinding
    private lateinit var venuesAdapter: VenuesAdapter
    private lateinit var venuesViewModel: VenuesViewModel
    private lateinit var teamsViewModel: TeamsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        venuesViewModel = ViewModelProvider(this)[VenuesViewModel::class.java]
        teamsViewModel = ViewModelProvider(this)[TeamsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStadiumsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        teamsViewModel.getTeams()

        observeTeamsAndExtractVenuesNames()

        binding.progressBar.visibility = View.VISIBLE
        binding.rvStadiums.visibility = View.GONE

        observeVenuesList()
        observeErrors()


    }

    private fun prepareRecyclerView() {
        venuesAdapter = VenuesAdapter()
        binding.rvStadiums.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = venuesAdapter
        }
    }

    private fun observeTeamsAndExtractVenuesNames(){
        teamsViewModel.observeTeamsLiveData().observe(viewLifecycleOwner) { teams ->
            val stadiumNames = teams.mapNotNull { it.strStadium }
            venuesViewModel.fetchAllVenues(stadiumNames)
        }
    }

    private fun observeErrors(){
        venuesViewModel.observeErrorLiveData().observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeVenuesList(){
        venuesViewModel.observeVenuesLiveData().observe(viewLifecycleOwner) { venues ->
            Log.d("VenuesFragment", "Total venues: ${venues.size}")
            venuesAdapter.setVenueList(venues)

            binding.progressBar.visibility = View.GONE
            binding.rvStadiums.visibility = View.VISIBLE
        }
    }

}