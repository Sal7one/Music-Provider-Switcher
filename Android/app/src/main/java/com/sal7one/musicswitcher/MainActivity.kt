package com.sal7one.musicswitcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.sal7one.musicswitcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var spotify_btn: ImageButton
    private lateinit var apple_btn: ImageButton
    private lateinit var anghami_btn: ImageButton
    private lateinit var update_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spotify_btn =  binding.spotifybtn
        apple_btn = binding.applemusicbtn
        anghami_btn =  binding.anghamibtn
        update_btn =  binding.updatebutton


        spotify_btn.setOnClickListener{changeViewBackground(it) }
        apple_btn.setOnClickListener{changeViewBackground(it)}
        anghami_btn.setOnClickListener{changeViewBackground(it)}
        update_btn.setOnClickListener {}
    }


    fun changeViewBackground(the_button: View){

        spotify_btn.setBackgroundColor(getColor(R.color.white))
        apple_btn.setBackgroundColor(getColor(R.color.white))
        anghami_btn.setBackgroundColor(getColor(R.color.white))

        the_button.setBackgroundColor(getColor(R.color.button_clicked))
    }
}