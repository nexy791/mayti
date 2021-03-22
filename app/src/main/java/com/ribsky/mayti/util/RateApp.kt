package com.ribsky.mayti.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ribsky.mayti.R
import java.util.*

class RateApp(private val context: Context) {

    private val alertTitle: String = "Оценить"
    private val alertBody: String =
        "Если тебе нравится приложение ${context.getString(R.string.app_name)}, пожалуйста, оцените его!"
    private val range: Int = 4


    private fun showDialog() {
        with(MaterialAlertDialogBuilder(context)) {
            setTitle(alertTitle)
            setMessage(alertBody)
            setPositiveButton("Оценить") { _, _ ->
                setWasShown()
                context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")))
            }
            setNegativeButton("Позже") { _, _ -> }
            show()
        }
    }

    private fun getWasShown(): Boolean {
        val mSettings: SharedPreferences =
            context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return mSettings.getBoolean("shown", false)
    }

    private fun setWasShown() {
        val mSettings: SharedPreferences =
            context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val editor = mSettings.edit()
        editor.putBoolean("shown", true)
        editor.apply()
    }

    private fun isCanBeShown(): Boolean {
        return Random().nextInt(range) == 0
    }

    fun initialization() {
        if (isCanBeShown() && !getWasShown()) {
            showDialog()
        }
    }
}