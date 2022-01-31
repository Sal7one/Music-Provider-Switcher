package com.sal7one.musicswitcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.sal7one.musicswitcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //private lateinit var viewModel: MainActicityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var spotify_btn: ImageButton
    private lateinit var apple_btn: ImageButton
    private lateinit var anghami_btn: ImageButton
    private lateinit var update_btn: Button

    private var currentProvider = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        viewModel = ViewModelProvider(this).get(MainActicityViewModel::class.java)

        spotify_btn =  binding.spotifybtn
        apple_btn = binding.applemusicbtn
        anghami_btn =  binding.anghamibtn
        update_btn =  binding.updatebutton


        spotify_btn.setOnClickListener{
            changeViewBackground(it)
            currentProvider = "spotify"
        }
        apple_btn.setOnClickListener{
            changeViewBackground(it)
            currentProvider = "applemusic"
        }
        anghami_btn.setOnClickListener{
            changeViewBackground(it)
            currentProvider = "anghami"
        }
        update_btn.setOnClickListener {
            if(currentProvider.isNotBlank()){
              updateProvider()
            }
        }
    }


    fun changeViewBackground(the_button: View){
        spotify_btn.setBackgroundColor(getColor(R.color.white))
        apple_btn.setBackgroundColor(getColor(R.color.white))
        anghami_btn.setBackgroundColor(getColor(R.color.white))
        the_button.setBackgroundColor(getColor(R.color.button_clicked))
    }

    fun updateProvider(){

        //currentProvider

    }
}