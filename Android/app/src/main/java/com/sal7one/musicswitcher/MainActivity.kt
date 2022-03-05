package com.sal7one.musicswitcher

import android.app.AlertDialog
import com.sal7one.musicswitcher.repository.DataStoreProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sal7one.musicswitcher.databinding.ActivityMainBinding
import com.sal7one.musicswitcher.controllers.MyViewModelFactory
import com.sal7one.musicswitcher.controllers.ApplicationViewModel
import com.sal7one.musicswitcher.utils.Constants


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ApplicationViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataStoreProvider: DataStoreProvider
    private var currentProvider = ""
    private var clickedMusicProvider = ""
    private var albumChoice = false
    private var playlistChoice = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreProvider = DataStoreProvider(applicationContext).getInstance()
        viewModel = ViewModelProvider(this, MyViewModelFactory(dataStoreProvider)).get(
            ApplicationViewModel::class.java
        )

        viewModel.chosenProvider.observe(this) {
            currentProvider = it
            changeViewBackground()
        }

        viewModel.playlistChoice.observe(this) {
            playlistChoice = it
        }

        viewModel.albumChoice.observe(this) {
            albumChoice = it
            changeCheckboxState()
        }

        binding.explainButton.setOnClickListener {
            alertDialogMaker()
        }

        binding.updateBtn.setOnClickListener {
            if (currentProvider.isNotBlank()) {
                updateProvider()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.chose_first_toast),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        binding.playlistCheckBox.setOnClickListener {
            playlistChoice = binding.playlistCheckBox.isChecked
        }
        binding.albumCheckBox.setOnClickListener {
            albumChoice = binding.albumCheckBox.isChecked
        }
    }

    private fun changeCheckboxState() {
        binding.playlistCheckBox.isChecked = playlistChoice
        binding.albumCheckBox.isChecked = albumChoice
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
        viewModel.saveData(currentProvider, playlistChoice, albumChoice)
        Toast.makeText(this, getString(R.string.data_updated_toast), Toast.LENGTH_SHORT).show()
    }

    private fun alertDialogMaker() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.explain_dialog_title))
            .setView(R.layout.explain_page)
            .setPositiveButton(getString(R.string.explain_positive_btn)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    fun buildClickListeners(musicButton: View) {
        clickedMusicProvider = resources.getResourceEntryName(musicButton.id)
        when (musicButton.id) {
            R.id.spotifyBtn -> currentProvider = Constants.SPOTIFY.link
            R.id.appleMusicBtn -> currentProvider = Constants.APPLE_MUSIC.link
            R.id.anghamiBtn -> currentProvider = Constants.ANGHAMI.link
            R.id.deezerBtn -> currentProvider = Constants.DEEZER.link
            R.id.ytMusicBtn -> currentProvider = Constants.YT_MUSIC.link
        }
        changeViewBackground()
    }
}