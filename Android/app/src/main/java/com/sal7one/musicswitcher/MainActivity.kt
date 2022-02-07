package com.sal7one.musicswitcher

import com.sal7one.musicswitcher.repository.DataStoreProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sal7one.musicswitcher.databinding.ActivityMainBinding
import com.sal7one.musicswitcher.controllers.MyViewModelFactory
import com.sal7one.musicswitcher.controllers.ApplicationViewModel
import com.sal7one.musicswitcher.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ApplicationViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var spotifyBtn: ImageButton
    private lateinit var appleBtn: ImageButton
    private lateinit var anghamiBtn: ImageButton
    private lateinit var deezerBtn: ImageButton
    private lateinit var ytBtn: ImageButton
    private lateinit var updateBtn: Button
    private lateinit var dataStoreProvider: DataStoreProvider
    private var currentProvider = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreProvider = DataStoreProvider(applicationContext).getInstance()

        viewModel = ViewModelProvider(this, MyViewModelFactory(dataStoreProvider)).get(
            ApplicationViewModel::class.java
        )

        spotifyBtn = binding.spotifyBtn
        appleBtn = binding.appleMusicBtn
        anghamiBtn = binding.anghamiBtn
        deezerBtn = binding.deezerBtn
        ytBtn = binding.ytMusicBtn
        updateBtn = binding.updateBtn

        viewModel.chosenProvider.observe(this, {
            currentProvider = it
            changeViewBackground()
        })

        spotifyBtn.setOnClickListener {
            currentProvider = Constants.SPOTIFY.link
            changeViewBackground()
        }
        appleBtn.setOnClickListener {
            currentProvider = Constants.APPLE_MUSIC.link
            changeViewBackground()
        }
        anghamiBtn.setOnClickListener {
            currentProvider = Constants.ANGHAMI.link
            changeViewBackground()
        }
        deezerBtn.setOnClickListener {
            currentProvider = Constants.DEEZER.link
            changeViewBackground()
        }
        ytBtn.setOnClickListener {
            currentProvider = Constants.YT_MUSIC.link
            changeViewBackground()
        }
        updateBtn.setOnClickListener {
            if (currentProvider.isNotBlank()) {
                updateProvider()
            }
        }
    }

    private fun changeViewBackground() {

        binding.appleMusicCard.setCardBackgroundColor(getColor(R.color.card_color))
        binding.spotifyCard.setCardBackgroundColor(getColor(R.color.card_color))
        binding.anghamiCard.setCardBackgroundColor(getColor(R.color.card_color))
        binding.deezerCard.setCardBackgroundColor(getColor(R.color.card_color))
        binding.ytMusicCard.setCardBackgroundColor(getColor(R.color.card_color))

        when (currentProvider) {
            Constants.APPLE_MUSIC.link -> binding.appleMusicCard.setCardBackgroundColor(getColor(R.color.apple_clicked))
            Constants.SPOTIFY.link -> binding.spotifyCard.setCardBackgroundColor(getColor(R.color.spotify_clicked))
            Constants.ANGHAMI.link -> binding.anghamiCard.setCardBackgroundColor(getColor(R.color.anghami_clicked))
            Constants.DEEZER.link -> binding.deezerCard.setCardBackgroundColor(getColor(R.color.deezer_clicked))
            Constants.YT_MUSIC.link -> binding.ytMusicCard.setCardBackgroundColor(getColor(R.color.ytMusic_clicked))
        }
    }

    private fun updateProvider() {
        viewModel.saveData(currentProvider)
        Toast.makeText(this, "Music provider updated", Toast.LENGTH_SHORT).show()
    }
}