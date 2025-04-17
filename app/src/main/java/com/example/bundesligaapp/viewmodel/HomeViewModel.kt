package com.example.bundesligaapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bundesligaapp.pojo.Event
import com.example.bundesligaapp.pojo.EventsList
import com.example.bundesligaapp.pojo.Team
import com.example.bundesligaapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var randomEventLiveData = MutableLiveData<Event>()

    fun getRandomEvent() {
        Log.d("HomeViewModel", "Llamando a getRandomEvent()")
        RetrofitInstance.api.getRandomEvent("4331", "2024-2025").enqueue(
            object : Callback<EventsList> {
                override fun onResponse(call: Call<EventsList>, response: Response<EventsList>) {
                    if (response.body() != null) {
                        val randomEvent: Event = response.body()!!.events.random()
                        randomEventLiveData.value = randomEvent
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<EventsList>, t: Throwable) {
                    Log.d("HomeViewModel", t.message.toString())
                }

            }
        )
    }

    fun observeRandomEventLiveData(): LiveData<Event> {
        return randomEventLiveData
    }
}