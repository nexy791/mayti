package com.ribsky.mayti.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ribsky.mayti.R
import com.ribsky.mayti.activities.IntroActivity
import com.ribsky.mayti.activities.LibActivity
import com.ribsky.mayti.activities.SplashActivity
import com.ribsky.mayti.utils.AlertsUtils
import com.ribsky.mayti.utils.ExtraUtils

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        findPreference<Preference>("pref_rate")?.setOnPreferenceClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=com.ribsky.mayti")
            )
            startActivity(browserIntent)
            true
        }

        findPreference<Preference>("pref_share")?.setOnPreferenceClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "mayti - сообщество поиска тиммейтов для любых игр.\n\nhttps://play.google.com/store/apps/details?id=com.ribsky.mayti"
            )
            sendIntent.type = "text/plain"
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            true
        }
        findPreference<Preference>("pref_dev")?.setOnPreferenceClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/dev?id=8181581032390861790")
            )
            startActivity(browserIntent)
            true
        }
        findPreference<Preference>("pref_support")?.setOnPreferenceClickListener {
            AlertsUtils(requireActivity()).alertSupport()
            true
        }
        findPreference<Preference>("pref_change")?.setOnPreferenceClickListener {
            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle("Изменить аккаунт")
                setMessage("Ты действительно хочешь изменить данные об аккаунте?")
                setCancelable(true)
                setPositiveButton("Изменить") { _, _ ->
                    startActivity(Intent(requireActivity(), IntroActivity::class.java))
                    requireActivity().finish()
                }
                setNegativeButton("нет") { _, _ -> }
                show()
            }
            true
        }

        findPreference<Preference>("pref_delete")?.setOnPreferenceClickListener {
            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle("Удалить аккаунт")
                setMessage("Ты действительно хочешь удалить аккаунт mayti? Это действие невозможно отменить")
                setCancelable(true)
                setPositiveButton("Удалить") { _, _ ->
                    startActivity(Intent(requireActivity(), SplashActivity::class.java))
                    val database: FirebaseDatabase =
                        Firebase.database(ExtraUtils.FIREBASE_DATABASE_ADDRESS)
                    database.reference.root.child(FirebaseAuth.getInstance().currentUser.uid)
                        .setValue(null)
                    FirebaseAuth.getInstance().signOut()
                    requireActivity().finish()
                }
                setNegativeButton("нет") { _, _ -> }
                show()
            }
            true
        }

        findPreference<Preference>("pref_logout")?.setOnPreferenceClickListener {
            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle("Выход")
                setMessage("Ты действительно хочешь выйти?")
                setCancelable(true)
                setPositiveButton("Выйти") { _, _ ->
                    startActivity(Intent(requireActivity(), SplashActivity::class.java))
                    FirebaseAuth.getInstance().signOut()
                    requireActivity().finish()
                }
                setNegativeButton("нет") { _, _ -> }
                show()
            }
            true
        }

        findPreference<Preference>("pref_terms")?.setOnPreferenceClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/nexy791/mayti/blob/master/policy.md")
            )
            startActivity(browserIntent)
            true
        }

        findPreference<Preference>("pref_lib")?.setOnPreferenceClickListener {
            startActivity(Intent(requireActivity(), LibActivity::class.java))
            true
        }

    }
}