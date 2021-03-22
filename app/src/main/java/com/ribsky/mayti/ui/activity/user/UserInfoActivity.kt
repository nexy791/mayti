package com.ribsky.mayti.ui.activity.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.chip.Chip
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.ActivityUserinfoBinding
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.model.user.UserModelParcelable
import com.ribsky.mayti.util.AlertsUtil
import com.ribsky.mayti.util.ExtraUtil

class UserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserinfoBinding

    private lateinit var user: UserModel

    private var currentCoin: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        user = (intent.getParcelableExtra<UserModelParcelable>("user")!!).fromParcelable()
        currentCoin = ExtraUtil().getLikes(this, Firebase.auth.currentUser.uid)

        binding.textViewName.text = user.fln
        binding.textViewDesc.text = user.bio

        binding.imageViewAvatar.load(user.photo) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_playstore)
            transformations(CircleCropTransformation())
        }
        binding.fabSend.setOnClickListener {
            if (currentCoin > 0
            ) {
                currentCoin--
                ExtraUtil().setLikes(
                    this,
                    Firebase.auth.currentUser.uid,
                    currentCoin
                )

                startActivity(
                    Intent.createChooser(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(user.social)
                        ), "Написать"
                    )
                )


            } else {

                AlertsUtil(this).alertNoLikes()

            }

        }

        binding.fabReport.setOnClickListener {
            AlertsUtil(this).alertSupport()
        }

        for (i in user.games) {
            if (i >= 0) {
                binding.chips.addView(Chip(binding.root.context).apply {
                    text = ExtraUtil.LIST_OF_GAMES[i]
                })
            }
        }


        binding.fabClose.setOnClickListener {
            onBackPressed()
        }
        binding.toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_OK)
        finish()
    }
}