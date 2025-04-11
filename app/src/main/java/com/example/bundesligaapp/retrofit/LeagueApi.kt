package com.example.bundesligaapp.retrofit

import com.example.bundesligaapp.pojo.EventsList
import com.example.bundesligaapp.pojo.Team
import com.example.bundesligaapp.pojo.TeamsList
import com.example.bundesligaapp.pojo.VenuesList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface LeagueApi {

    @GET("eventsseason.php")
    fun getRandomEvent(
        @Query("id") leagueId: String,
        @Query("s") season: String
    ): Call<EventsList>

    @GET("search_all_teams.php")
    fun getTeams(@Query("l") leagueName: String): Call<TeamsList>

    @GET("searchvenues.php")
    suspend fun getVenue(@Query("t") stadiumName: String): VenuesList

    @GET("searchteams.php")
    fun getTeamDetails(@Query("t") teamName: String): Call<TeamsList>

}