package com.sal7one.musicswitcher

import DataStoreProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sal7one.musicswitcher.databinding.ActivityMainBinding
import com.sal7one.musicswitcher.controllers.MyViewModelFactory
import com.sal7one.musicswitcher.controllers.ApplicationViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ApplicationViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var spotify_btn: ImageButton
    private lateinit var apple_btn: ImageButton
    private lateinit var anghami_btn: ImageButton
    private lateinit var deezer_btn: ImageButton
    private lateinit var yt_btn: ImageButton
    private lateinit var update_btn: Button
    private lateinit var dataStoreProvider: DataStoreProvider

    private var currentProvider = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreProvider = DataStoreProvider.getInstance(this)

        viewModel = ViewModelProvider(this, MyViewModelFactory(dataStoreProvider)).get(
            ApplicationViewModel::class.java)

        spotify_btn =  binding.spotifybtn
        apple_btn = binding.applemusicbtn
        anghami_btn =  binding.anghamibtn
        deezer_btn =  binding.deezerbtn
        yt_btn =  binding.ytmusicbtn
        update_btn =  binding.updatebutton

        viewModel.choosen_Provider.observe(this, Observer { it->
            currentProvider = it
            changeViewBackground()
        })

        spotify_btn.setOnClickListener{
            currentProvider = "open.spotify.com"
            changeViewBackground()
        }
        apple_btn.setOnClickListener{
            currentProvider = "music.apple.com"
            changeViewBackground()
        }
        anghami_btn.setOnClickListener{
            currentProvider = "play.anghami.com"
            changeViewBackground()
        }
        deezer_btn.setOnClickListener{
            currentProvider = "deezer.com"
            changeViewBackground()
        }
        yt_btn.setOnClickListener{
            currentProvider =  "music.youtube.com"
            changeViewBackground()
        }
        update_btn.setOnClickListener {
            if(currentProvider.isNotBlank()){
              updateProvider()
            }
        }

    }


    fun changeViewBackground(){
        spotify_btn.setBackgroundColor(getColor(R.color.white))
        apple_btn.setBackgroundColor(getColor(R.color.white))
        anghami_btn.setBackgroundColor(getColor(R.color.white))
        deezer_btn.setBackgroundColor(getColor(R.color.white))
        yt_btn.setBackgroundColor(getColor(R.color.white))

        when(currentProvider){
            "open.spotify.com" -> spotify_btn.setBackgroundColor(getColor(R.color.button_clicked))
            "music.apple.com" -> apple_btn.setBackgroundColor(getColor(R.color.button_clicked))
            "play.anghami.com" -> anghami_btn.setBackgroundColor(getColor(R.color.button_clicked))
            "deezer.com" -> deezer_btn.setBackgroundColor(getColor(R.color.button_clicked))
            "music.youtube.com" -> yt_btn.setBackgroundColor(getColor(R.color.button_clicked))
        }
    }

    fun updateProvider(){
        viewModel.SaveData(currentProvider)
        Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()
    }
}