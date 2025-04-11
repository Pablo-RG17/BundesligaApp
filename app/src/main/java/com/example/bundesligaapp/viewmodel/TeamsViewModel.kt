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

class TeamsViewModel : ViewModel() {

    private val teamsLiveData = MutableLiveData<List<Team>>()
    private val teamDetailLiveData = MutableLiveData<Team>()

    fun getTeams() {
        Log.d("TeamsViewModel", "getTeams() called")
        RetrofitInstance.api.getTeams("German Bundesliga").enqueue(object : Callback<TeamsList> {
            override fun onResponse(call: Call<TeamsList>, response: Response<TeamsList>) {
                response.body()?.teams?.let { teamList ->
                        Log.d("TeamsViewModel", "Teams received: ${teamList.size}")
                        teamsLiveData.postValue(teamList)
                    }
            }

            override fun onFailure(call: Call<TeamsList>, t: Throwable) {
                Log.d("TeamsViewModel", t.message.toString())
            }

        })
    }

    fun getTeamDetails(teamName:String){
        RetrofitInstance.api.getTeamDetails(teamName).enqueue(object : Callback<TeamsList> {
            override fun onResponse(call: Call<TeamsList>, response: Response<TeamsList>) {
                response.body()?.teams?.let { team->
                    teamDetailLiveData.postValue(team[0])
                }
            }

            override fun onFailure(call: Call<TeamsList>, t: Throwable) {
                Log.d("TeamsViewModel", t.message.toString())
            }

        })
    }

    fun observeTeamsLiveData(): LiveData<List<Team>> {
        return teamsLiveData
    }

    fun observeTeamDetailsLiveData(): MutableLiveData<Team>{
        return teamDetailLiveData
    }
}