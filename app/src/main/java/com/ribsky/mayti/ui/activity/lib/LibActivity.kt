package com.ribsky.mayti.ui.activity.lib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ribsky.mayti.databinding.ActivityLibBinding

class LibActivity : AppCompatActivity() {

    lateinit var binding: ActivityLibBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        binding.topAppBar.setNavigationOnClickListener { onBackPressed() }

    }
}