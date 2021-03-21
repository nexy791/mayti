package com.ribsky.mayti.ui.activity.blocked

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ribsky.mayti.databinding.ActivityBlockedBinding
import com.ribsky.mayti.util.AlertsUtil

class BlockedActivity : AppCompatActivity() {

    lateinit var binding: ActivityBlockedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlockedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.support.setOnClickListener {
            AlertsUtil(this).alertSupport()
        }
    }
}