package com.example.bundesligaapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bundesligaapp.pojo.Team
import com.example.bundesligaapp.pojo.TeamsList
import com.example.bundesligaapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private var allTeams: List<Team> = emptyList()
    private val filteredTeamsLiveData = MutableLiveData<List<Team>>()
    fun observeFilteredTeamsLiveData(): LiveData<List<Team>> = filteredTeamsLiveData

    fun getTeamsForSearch() {
        Log.d("SearchViewModel", "getTeams() called")
        RetrofitInstance.api.getTeams("German Bundesliga").enqueue(object :
            Callback<TeamsList> {
            override fun onResponse(call: Call<TeamsList>, response: Response<TeamsList>) {
                response.body()?.teams?.let { teamList ->
                    Log.d("SearchViewModel", "Teams received: ${teamList.size}")
                    allTeams = teamList
                }
            }

            override fun onFailure(call: Call<TeamsList>, t: Throwable) {
                Log.d("SearchViewModel", t.message.toString())
            }
        })
    }

    fun filteredTeams(searchQuery: String) {

        if (allTeams.isNullOrEmpty()) {
            filteredTeamsLiveData.postValue(arrayListOf())
            return
        }

        if (searchQuery.isBlank()) {
            filteredTeamsLiveData.postValue(arrayListOf())
            return
        }

        val teamFiltered = allTeams.filter { team ->
            team.strTeam?.contains(searchQuery, ignoreCase = true) == true
        }
        filteredTeamsLiveData.postValue(ArrayList(teamFiltered))
    }

}