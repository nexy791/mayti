package com.ribsky.mayti.ui.activity.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.ribsky.mayti.databinding.ActivitySettingsBinding
import com.ribsky.mayti.ui.fragment.settings.SettingsFragment

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        binding.toolBar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        supportFragmentManager.beginTransaction().replace(binding.container.id, SettingsFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()

    }
}