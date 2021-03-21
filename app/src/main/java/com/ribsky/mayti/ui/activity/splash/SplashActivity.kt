package com.ribsky.mayti.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ribsky.mayti.databinding.ActivitySplashBinding
import com.ribsky.mayti.ui.activity.intro.IntroActivity
import com.ribsky.mayti.ui.activity.main.MainActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (Firebase.auth.currentUser == null) {
            Timer("").schedule(800) {
                startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
                finish()
            }
        } else {
            Timer("").schedule(800) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}