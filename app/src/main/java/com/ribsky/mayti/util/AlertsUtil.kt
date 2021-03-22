package com.ribsky.mayti.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ribsky.mayti.ui.activity.splash.SplashActivity
import com.ribsky.mayti.ui.fragment.profile.ProfileFragment


class AlertsUtil(private val activity: Activity) {

    fun alertExit() {
        with(MaterialAlertDialogBuilder(activity)) {
            setTitle("Выход")
            setCancelable(true)
            setMessage("Ты действительно хочешь выйти с mayti?")
            setPositiveButton("Выход") { _, _ -> activity.finish() }
            setNegativeButton(
                "Отмена"
            ) { _, _ -> }
            show()
        }

    }

    fun alertCriticalError() {
        with(MaterialAlertDialogBuilder(activity)) {
            setTitle("Ошибка")
            setCancelable(false)
            setMessage("Произошла ошибка загрузки данных. Пожалуйста, перезайди в mayti")
            setPositiveButton("Перезайти") { _, _ -> activity.finish() }
            setNegativeButton(
                "Поддержка"
            ) { _, _ -> alertSupport() }
            show()
        }
    }

    fun alertSupport() {
        with(MaterialAlertDialogBuilder(activity)) {
            setTitle("Поддержка")
            setCancelable(true)
            setMessage("Напиши на почту, чтобы узнать ответ на свой вопрос или пожаловаться")
            setNegativeButton(
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
            show()
        }
    }

    fun alertReLogin() {
        with(MaterialAlertDialogBuilder(activity)) {
            setTitle("Упс!")
            setCancelable(false)
            setMessage("Похоже, что пришло время перезайти в аккаунт mayti!")
            setPositiveButton("Перезайти") { _, _ ->
                activity.startActivity(Intent(activity, SplashActivity::class.java))
                activity.finish()
            }
            show()
        }
    }

    fun alertLikesInfo(fragment: ProfileFragment) {
        with(MaterialAlertDialogBuilder(activity)) {
            setTitle("Получить лайки")
            setCancelable(false)
            setMessage("Ты можешь получить 1 лайк каждые 12 часов или с моментально помощью рекламы")
            setPositiveButton("Продолжить") { _, _ ->
                fragment.getLikes()
            }
            setNegativeButton("Отмена") { _, _ -> }
            show()
        }
    }

    fun alertNoLikes() {
        with(MaterialAlertDialogBuilder(activity)) {
            setTitle("Не хватает лайков!")
            setCancelable(true)
            setMessage("У тебя не хватает лайков. Ты можешь получить лайки, посмотрев рекламу. Для этого перейди в свой Профиль")
            setPositiveButton("Понятно") { _, _ -> }
            setNegativeButton("Отмена") { _, _ -> }
            show()
        }
    }
}