package com.ribsky.mayti.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ribsky.mayti.databinding.ActivityBlockedBinding
import com.ribsky.mayti.utils.AlertsUtils

class BlockedActivity : AppCompatActivity() {

    lateinit var binding: ActivityBlockedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlockedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.support.setOnClickListener {
            AlertsUtils(this).alertSupport()
        }
    }
}