package com.example.bundesligaapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bundesligaapp.pojo.Venue
import com.example.bundesligaapp.pojo.VenuesList
import com.example.bundesligaapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VenuesViewModel : ViewModel() {

    private val venuesLiveData = MutableLiveData<List<Venue>>()
    private val errorLiveData = MutableLiveData<String>()

    fun fetchAllVenues(stadiumNames: List<String>) {
        if (venuesLiveData.value.isNullOrEmpty()) {
            viewModelScope.launch {
                val venuesList = mutableListOf<Venue>()

                stadiumNames.forEach { venueName ->
                    try {
                        val response = RetrofitInstance.api.getVenue(venueName)
                        response.venues?.firstOrNull()?.let { venuesList.add(it) }
                    } catch (e: Exception) {
                        Log.e("VenuesViewModel", "Error fetching $venueName: ${e.message}")
                        errorLiveData.postValue("Error al obtener $venueName")
                    }
                }

                venuesLiveData.postValue(venuesList)
            }
        }
    }

    fun observeVenuesLiveData(): LiveData<List<Venue>> {
        return venuesLiveData
    }
    fun observeErrorLiveData(): LiveData<String> = errorLiveData

}