package com.sal7one.musicswitcher

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sal7one.musicswitcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

   private lateinit var viewModel: MainActvityViewModel
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

        viewModel = ViewModelProvider(this, MyViewModelFactory(this.getApplication())).get(MainActvityViewModel::class.java)


        spotify_btn =  binding.spotifybtn
        apple_btn = binding.applemusicbtn
        anghami_btn =  binding.anghamibtn
        update_btn =  binding.updatebutton



        viewModel.choosen_Provider.observe(this, Observer { it->
            currentProvider = it
            changeViewBackground()
        })

        spotify_btn.setOnClickListener{
            currentProvider = "spotify"
            changeViewBackground()
        }
        apple_btn.setOnClickListener{
            currentProvider = "applemusic"
            changeViewBackground()
        }
        anghami_btn.setOnClickListener{
            currentProvider = "anghami"
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

        when(currentProvider){
            "spotify" -> spotify_btn.setBackgroundColor(getColor(R.color.button_clicked))
            "applemusic" -> apple_btn.setBackgroundColor(getColor(R.color.button_clicked))
            "anghami" -> anghami_btn.setBackgroundColor(getColor(R.color.button_clicked))
        }
    }

    fun updateProvider(){
        viewModel.SaveData(currentProvider)
        Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()
    }
}