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

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}