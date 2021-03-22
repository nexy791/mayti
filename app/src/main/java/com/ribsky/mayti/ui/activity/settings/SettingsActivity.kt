package com.ribsky.mayti.ui.activity.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        binding.toolBar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.navigate(R.id.settingsFragment)

    }
}