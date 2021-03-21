package com.ribsky.mayti.ui.activity.intro

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.ActivityIntroBinding
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.util.AlertsUtil

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding


    private lateinit var navController: NavController
    private val listFragments: List<Int> =
        listOf(
            R.id.intro1Fragment,
            R.id.intro2Fragment,
            R.id.intro3Fragment,
            R.id.intro4Fragment,
            R.id.intro5Fragment
        )
    private val listTitles: List<String> =
        listOf("Знакомство 1/4", "Выбери игры 2/4", "Почти всё 3/4", "Связь 4/4", "Готово")


    lateinit var currentAccount: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        goFragment(0)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_support -> {
                AlertsUtil(this).alertSupport()
            }
        }
        return true
    }

    fun goFragment(i: Int) {
        navController.navigate(listFragments[i])
        binding.toolBar.title = listTitles[i]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_toolbar_menu, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onBackPressed() {
        AlertsUtil(this).alertExit()
    }
}