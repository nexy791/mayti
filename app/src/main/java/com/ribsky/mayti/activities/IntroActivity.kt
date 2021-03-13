package com.ribsky.mayti.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.ActivityIntroBinding
import com.ribsky.mayti.fragments.*
import com.ribsky.mayti.model.UserModel
import com.ribsky.mayti.utils.AlertsUtils

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    private val fragmentIntro1: Intro1Fragment = Intro1Fragment()
    private val fragmentIntro2: Intro2Fragment = Intro2Fragment()
    private val fragmentIntro3: Intro3Fragment = Intro3Fragment()
    private val fragmentIntro4: Intro4Fragment = Intro4Fragment()
    private val fragmentIntro5: Intro5Fragment = Intro5Fragment()

    val listFragments: List<Fragment> =
        listOf(fragmentIntro1, fragmentIntro2, fragmentIntro3, fragmentIntro4, fragmentIntro5)
    val listTitles: List<String> =
        listOf("Знакомство 1/4", "Выбери игры 2/4", "Почти всё 3/4", "Связь 4/4", "Готово")


    lateinit var currentAccount: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        goFragment(0)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_support -> {
                AlertsUtils(this).alertSupport()
                true
            }
            else -> true
        }
    }

    fun goFragment(i: Int) {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, listFragments[i])
            .commit()
        binding.toolBar.title = listTitles[i]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_toolbar_menu, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onBackPressed() {
        AlertsUtils(this).alertExit()
    }
}