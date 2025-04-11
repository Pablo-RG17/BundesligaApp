package com.example.bundesligaapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.bundesligaapp.R
import com.example.bundesligaapp.databinding.ActivityTeamBinding
import com.example.bundesligaapp.fragments.TeamsFragment
import com.example.bundesligaapp.viewmodel.TeamsViewModel

class TeamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamBinding
    private lateinit var idTeam: String
    private lateinit var nameTeam: String
    private lateinit var badgeTeam: String
    private lateinit var descriptionTeam: String
    private lateinit var teamsViewModel: TeamsViewModel
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        getInformationFromIntent()
        setInformationInViews()

        teamsViewModel = ViewModelProvider(this)[TeamsViewModel::class.java]

        teamsViewModel.getTeamDetails(nameTeam)
        observeTeamDetailsLiveData()

        onYoutubeImageClick()

    }

    private fun getInformationFromIntent() {
        val intent = intent
        idTeam = intent.getStringExtra(TeamsFragment.TEAM_ID)!!
        nameTeam = intent.getStringExtra(TeamsFragment.TEAM_NAME)!!
        badgeTeam = intent.getStringExtra(TeamsFragment.TEAM_BADGE)!!
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(badgeTeam)
            .into(binding.ivTeamLogo)

        binding.tvTeamName.text = nameTeam


    }

    private fun observeTeamDetailsLiveData() {
        teamsViewModel.observeTeamDetailsLiveData().observe(this) { team ->
            binding.tvTeamCity.text = getString(R.string.city_label, team.strLocation)
            binding.tvStadiumName.text = getString(R.string.stadium_label, team.strStadium)
            binding.tvTeamDescription.text = team.strDescriptionEN ?: descriptionTeam

            Log.d("TeamActivity", "YouTube Link recibido: ${team.strYoutube}")
            youtubeLink = team.strYoutube ?: ""
        }
    }

    private fun initListeners() {
        binding.ivBackArrow.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun onYoutubeImageClick() {
        binding.ivYouTube.setOnClickListener {
            var link = youtubeLink?.trim()

            if (!link.isNullOrEmpty()) {
                if (!link.startsWith("http")) {
                    link = "https://$link"
                }

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)

            } else {
                Toast.makeText(this, "Enlace de YouTube no disponible", Toast.LENGTH_SHORT).show()
            }
        }
    }
}