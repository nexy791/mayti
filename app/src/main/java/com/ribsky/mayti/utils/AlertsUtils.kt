package com.ribsky.mayti.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ribsky.mayti.activities.SplashActivity
import com.ribsky.mayti.fragments.ProfileFragment


class AlertsUtils(private val activity: Activity) {

    fun alertExit() {
        val alertDialog = MaterialAlertDialogBuilder(activity)
        alertDialog.setTitle("Выход")
        alertDialog.setCancelable(true)
        alertDialog.setMessage("Ты действительно хочешь выйти с mayti?")
        alertDialog.setPositiveButton("Выход") { _, _ -> activity.finish() }
        alertDialog.setNegativeButton(
            "Отмена"
        ) { _, _ -> activity.finish() }
        alertDialog.show()
    }

    fun alertCriticalError() {
        val alertDialog = MaterialAlertDialogBuilder(activity)
        alertDialog.setTitle("Ошибка")
        alertDialog.setCancelable(false)
        alertDialog.setMessage("Произошла ошибка загрузки данных. Пожалуйста, перезайди в mayti")
        alertDialog.setPositiveButton("Перезайти") { _, _ -> activity.finish() }
        alertDialog.setNegativeButton(
            "Поддержка"
        ) { _, _ -> alertSupport() }
        alertDialog.show()
    }

    fun alertSupport() {
        val alertDialog = MaterialAlertDialogBuilder(activity)
        alertDialog.setTitle("Поддержка")
        alertDialog.setCancelable(true)
        alertDialog.setMessage("Напиши на почту, чтобы узнать ответ на свой вопрос или пожаловаться")
        /*alertDialog.setPositiveButton("VK") { _, _ ->

        activity.startActivity(
            Intent.createChooser(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(ExtraUtils.VK_SMS_LINK)
                ), "Написать"
            )
        )

    }*/
        alertDialog.setNegativeButton(
            "Email"
        ) { _, _ ->
            activity.startActivity(
                Intent.createChooser(
                    Intent(
                        Intent.ACTION_SENDTO,
                        Uri.parse("mailto:nexy791@gmail.com")
                    ), "Написать"
                )
            )
        }

        alertDialog.show()
    }

    fun alertRelogin() {
        val alertDialog = MaterialAlertDialogBuilder(activity)
        alertDialog.setTitle("Упс!")
        alertDialog.setCancelable(false)
        alertDialog.setMessage("Похоже, что пришло время перезайти в аккаунт mayti!")
        alertDialog.setPositiveButton("Перезайти") { _, _ ->
            activity.startActivity(Intent(activity, SplashActivity::class.java))
            activity.finish()
        }
        alertDialog.show()
    }

    fun alertWhatIsLikes(fragment: ProfileFragment) {
        val alertDialog = MaterialAlertDialogBuilder(activity)
        alertDialog.setTitle("Получить лайки")
        alertDialog.setCancelable(false)
        alertDialog.setMessage("Ты можешь получить 1 лайк каждые 12 часов или с моментально помощью рекламы")
        alertDialog.setPositiveButton("Продолжить") { _, _ ->
            fragment.getLikes()
        }
        alertDialog.setNegativeButton("Отмена") { _, _ -> }
        alertDialog.show()
    }

    fun alertNoLikes() {
        val alertDialog = MaterialAlertDialogBuilder(activity)
        alertDialog.setTitle("Не хватает лайков!")
        alertDialog.setCancelable(true)
        alertDialog.setMessage("У тебя не хватает лайков. Ты можешь получить лайки, посмотрев рекламу. Для этого перейди в свой Профиль")
        alertDialog.setPositiveButton("Понятно") { _, _ -> }
        alertDialog.setNegativeButton("Отмена") { _, _ -> }
        alertDialog.show()
    }


}