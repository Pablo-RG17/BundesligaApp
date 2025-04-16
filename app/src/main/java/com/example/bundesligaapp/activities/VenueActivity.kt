package com.example.bundesligaapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.bundesligaapp.R
import com.example.bundesligaapp.databinding.ActivityVenueBinding
import com.example.bundesligaapp.fragments.StadiumsFragment

class VenueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVenueBinding
    private lateinit var idVenue: String
    private lateinit var nameVenue: String
    private lateinit var imageVenue: String
    private lateinit var locationVenue: String
    private lateinit var capacityVenue: String
    private lateinit var descriptionVenue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVenueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        getInformationFromIntent()
        setInformationInViews()
    }

    private fun getInformationFromIntent() {
        val intent = intent
        idVenue = intent.getStringExtra(StadiumsFragment.VENUE_ID)!!
        nameVenue = intent.getStringExtra(StadiumsFragment.VENUE_NAME)!!
        imageVenue = intent.getStringExtra(StadiumsFragment.VENUE_IMAGE)!!
        locationVenue = intent.getStringExtra(StadiumsFragment.VENUE_LOCATION)!!
        capacityVenue = intent.getStringExtra(StadiumsFragment.VENUE_CAPACITY)!!
        descriptionVenue = intent.getStringExtra(StadiumsFragment.VENUE_DESCRIPTION)!!
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(imageVenue.takeIf { !it.isNullOrEmpty() })
            .placeholder(R.drawable.placeholder_stadium)
            .error(R.drawable.placeholder_stadium)
            .into(binding.ivVenueImage)

        binding.tvVenueName.text = nameVenue
        binding.tvLocation.text = locationVenue
        binding.tvCapacity.text = capacityVenue
        binding.tvVenueDescription.text = descriptionVenue
    }


    private fun initListeners() {
        binding.ivBackArrow.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}