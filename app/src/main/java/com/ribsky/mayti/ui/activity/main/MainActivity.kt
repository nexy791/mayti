package com.ribsky.mayti.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.ActivityMainBinding
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.presentation.presenter.main.MainActivityPresenter
import com.ribsky.mayti.presentation.view.main.MainContract
import com.ribsky.mayti.ui.activity.blocked.BlockedActivity
import com.ribsky.mayti.ui.activity.settings.SettingsActivity
import com.ribsky.mayti.util.AlertsUtil
import com.ribsky.mayti.util.ExtraUtil
import com.ribsky.mayti.util.RateApp
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var mPresenter: MainContract.Presenter
    private lateinit var binding: ActivityMainBinding

    lateinit var currentAccount: UserModel
    var currentCoin: Int = 0
    var users: ArrayList<UserModel> = ArrayList()


    private var isLoaded: Boolean = false
    var mRewardedAd: RewardedAd? = null

    private val listFragments: List<Int> =
        listOf(R.id.searchFragment, R.id.ratingFragment, R.id.profileFragment)
    private val listTitles: List<String> = listOf("Поиск", "Рейтинг", "Аккаунт")
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        navController = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)

        mPresenter = MainActivityPresenter(this)
        mPresenter.onCreate()

        initNavigationView()
        MobileAds.initialize(this) {}


        RateApp(this).initialization()
        initAd()

    }


    fun initAd() {

        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            this,
            ExtraUtil.AD_NORMAL_CODE,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mRewardedAd = null
                    Snackbar.make(binding.root, "Реклама еще не готова", Snackbar.LENGTH_SHORT)
                        .show()
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                }
            })

        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Snackbar.make(binding.root, "Реклама еще не готова", Snackbar.LENGTH_SHORT).show()
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is dismissed.
                // Don't set the ad reference to null to avoid showing the ad a second time.
                mRewardedAd = null
            }
        }


    }

    fun updateBadger() {

        binding.bottom.getOrCreateBadge(R.id.item_account).apply {
            backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.green)
            number = currentCoin
        }
    }

    fun initNavigationView() {

        binding.bottom.setOnNavigationItemSelectedListener {

            if (isLoaded) {

                when (it.itemId) {

                    R.id.item_search -> {
                        goFragment(0)
                    }
                    R.id.item_rating -> {
                        goFragment(1)
                    }
                    R.id.item_account -> {
                        goFragment(2)
                    }

                }
                true
            } else {
                false
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.item_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> {
                false
            }
        }
    }

    override fun criticalError() {
        AlertsUtil(this).alertCriticalError()
    }

    override fun criticalErrorAuth() {
        AlertsUtil(this).alertReLogin()
        Firebase.auth.signOut()
    }

    override fun startApp(user: UserModel, users: ArrayList<UserModel>) {
        isLoaded = true
        currentAccount = user
        this.users = users
        goFragment(0)

        ExtraUtil().addReward(this, currentAccount.uid)

        currentCoin = ExtraUtil().getLikes(this, currentAccount.uid)
        updateBadger()

        EventBus.getDefault().post(users)
    }

    override fun userBlocked() {
        startActivity(Intent(this, BlockedActivity::class.java))
        finish()
    }

    fun sendUsers() {
        if (users.isNotEmpty()) EventBus.getDefault().post(users)
    }

    fun goFragment(i: Int) {
        navController.navigate(listFragments[i])
        binding.toolBar.title = listTitles[i]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_toolbar_main_menu, menu)
        return true
    }

    override fun onBackPressed() {
        AlertsUtil(this).alertExit()
    }
}