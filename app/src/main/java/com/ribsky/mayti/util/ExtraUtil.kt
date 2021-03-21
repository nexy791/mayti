package com.ribsky.mayti.util

import android.content.Context
import com.securepreferences.SecurePreferences
import java.text.SimpleDateFormat
import java.util.*


class ExtraUtil {

    companion object {
        const val SHARED_PREFS_NAME: String = "com.ribsky.mayti"
        const val REQUEST_CODE_REGISTER_FIREBASE: Int = 100
        const val RESULT_INFO: Int = 101
        const val FIREBASE_DATABASE_ADDRESS: String =
            "https://mayti-bbb73-default-rtdb.europe-west1.firebasedatabase.app/"

        val LIST_OF_GAMES: List<String> = listOf(
            "Minecraft PE",
            "Clash Royale",
            "Clash of Clans",
            "Brawl Stars",
            "PUBG: Mobile",
            "Standoff 2",
            "Among us",
            "Roblox",
            "World of Tanks Blitz"
        )

        const val AD_TEST_CODE: String = "ca-app-pub-3940256099942544/5224354917"
        const val AD_NORMAL_CODE: String = "ca-app-pub-4406747838048228/7515642683"

        const val LINK_PRIVACY_POLICY: String =
            "https://github.com/nexy791/mayti/blob/master/policy.md"

    }

    fun getLikes(context: Context, uid: String): Int {
        return SecurePreferences(context, uid, SHARED_PREFS_NAME)
            .getInt("likes", 5)
    }

    fun setLikes(context: Context, uid: String, count: Int) {
        SecurePreferences(context, uid, SHARED_PREFS_NAME).edit().putInt("likes", count).apply()
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date())
    }

    fun getTimeFromShared(context: Context, uid: String): String {

        val time: String = SecurePreferences(context, uid, SHARED_PREFS_NAME)
            .getString("fl", "")!!

        return if (time.isEmpty()) {
            setTimeToShared(context, uid)
            getCurrentDate()
        } else {
            time
        }


    }

    fun setTimeToShared(context: Context, uid: String) {
        SecurePreferences(context, uid, SHARED_PREFS_NAME)
            .edit().putString("fl", getCurrentDate()).apply()
    }

    fun addReward(context: Context, uid: String): Int {

        val diffed: Int = diffTime(
            getTimeFromShared(context, uid).toDate(SimpleDateFormat("yyyy-MM-dd HH:mm:ss")),
            getCurrentDate().toDate(SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
        )

        when {
            diffed >= 12 -> {

                val coins: Int = (diffed / 12)

                ExtraUtil().setLikes(
                    context, uid,
                    ExtraUtil().getLikes(context, uid) + coins
                )

                setTimeToShared(context, uid)

                return coins
            }
            diffed < 0 -> {
                ExtraUtil().setLikes(
                    context, uid,
                    0
                )
                setTimeToShared(context, uid)
                return 0
            }
            else -> {
                return 0
            }
        }


    }

    private fun diffTime(startDate: Date, endDate: Date): Int {

        val different = endDate.time - startDate.time

        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val elapsedHours = different / hoursInMilli

        return elapsedHours.toInt()
    }

    private fun String.toDate(format: SimpleDateFormat): Date {
        return format.parse(this)!!
    }
}