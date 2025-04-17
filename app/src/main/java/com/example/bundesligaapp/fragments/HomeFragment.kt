package com.example.bundesligaapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bundesligaapp.R
import com.example.bundesligaapp.activities.MainActivity
import com.example.bundesligaapp.databinding.FragmentHomeBinding
import com.example.bundesligaapp.pojo.Event
import com.example.bundesligaapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomEvent: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("HomeFragment", "onViewCreated: llamando a getRandomEvent()")
        viewModel.getRandomEvent()
        observeRandomEvent()
        onMoreInfoClick()
        onSearchIconClick()
    }

    private fun onMoreInfoClick() {
        val webSiteLink = "https://www.bundesliga.com/en/bundesliga"
        binding.tvMoreInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webSiteLink))
            startActivity(intent)
        }
    }

    private fun observeRandomEvent() {
        viewModel.observeRandomEventLiveData().observe(viewLifecycleOwner, { event ->
            Log.d("HomeFragment", "Evento recibido: ${event?.strEvent}")
            Log.d("HomeFragment", "Imagen URL: ${event?.strThumb}")

            event?.let {
                Glide.with(this@HomeFragment)
                    .load(event.strThumb)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(binding.ivLatestResults)

                this.randomEvent = event
            }
        })
    }


    private fun onSearchIconClick() {
        binding.ivSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

}