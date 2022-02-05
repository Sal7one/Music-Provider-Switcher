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

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ApplicationViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var spotifyBtn: ImageButton
    private lateinit var applebtn: ImageButton
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

        spotifyBtn = binding.spotifybtn
        applebtn = binding.applemusicbtn
        anghamiBtn = binding.anghamibtn
        deezerBtn = binding.deezerbtn
        ytBtn = binding.ytmusicbtn
        updateBtn = binding.updatebutton

        viewModel.choosenProvider.observe(this, {
            currentProvider = it
            changeViewBackground()
        })

        spotifyBtn.setOnClickListener {
            currentProvider = "open.spotify.com"
            changeViewBackground()
        }
        applebtn.setOnClickListener {
            currentProvider = "music.apple.com"
            changeViewBackground()
        }
        anghamiBtn.setOnClickListener {
            currentProvider = "play.anghami.com"
            changeViewBackground()
        }
        deezerBtn.setOnClickListener {
            currentProvider = "deezer.com"
            changeViewBackground()
        }
        ytBtn.setOnClickListener {
            currentProvider = "music.youtube.com"
            changeViewBackground()
        }
        updateBtn.setOnClickListener {
            if (currentProvider.isNotBlank()) {
                updateProvider()
            }
        }
    }

    private fun changeViewBackground() {
        spotifyBtn.setBackgroundColor(getColor(R.color.white))
        applebtn.setBackgroundColor(getColor(R.color.white))
        anghamiBtn.setBackgroundColor(getColor(R.color.white))
        deezerBtn.setBackgroundColor(getColor(R.color.white))
        ytBtn.setBackgroundColor(getColor(R.color.white))

        when (currentProvider) {
            "open.spotify.com" -> spotifyBtn.setBackgroundColor(getColor(R.color.button_clicked))
            "music.apple.com" -> applebtn.setBackgroundColor(getColor(R.color.button_clicked))
            "play.anghami.com" -> anghamiBtn.setBackgroundColor(getColor(R.color.button_clicked))
            "deezer.com" -> deezerBtn.setBackgroundColor(getColor(R.color.button_clicked))
            "music.youtube.com" -> ytBtn.setBackgroundColor(getColor(R.color.button_clicked))
        }
    }

    private fun updateProvider() {
        viewModel.saveData(currentProvider)
        Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()
    }
}