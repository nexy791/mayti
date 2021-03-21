package com.ribsky.mayti.ui.fragment.settings

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
import com.ribsky.mayti.ui.activity.intro.IntroActivity
import com.ribsky.mayti.ui.activity.lib.LibActivity
import com.ribsky.mayti.ui.activity.splash.SplashActivity
import com.ribsky.mayti.util.AlertsUtil
import com.ribsky.mayti.util.ExtraUtil

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        findPreference<Preference>("pref_rate")?.setOnPreferenceClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.ribsky.mayti")
                )
            )
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
            startActivity(Intent.createChooser(sendIntent, null))
            true
        }
        findPreference<Preference>("pref_dev")?.setOnPreferenceClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/dev?id=8181581032390861790")
                )
            )
            true
        }
        findPreference<Preference>("pref_support")?.setOnPreferenceClickListener {
            AlertsUtil(requireActivity()).alertSupport()
            true
        }
        findPreference<Preference>("pref_change")?.setOnPreferenceClickListener {
            with(MaterialAlertDialogBuilder(requireContext())) {
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
            with(MaterialAlertDialogBuilder(requireContext())) {
                setTitle("Удалить аккаунт")
                setMessage("Ты действительно хочешь удалить аккаунт mayti? Это действие невозможно отменить")
                setCancelable(true)
                setPositiveButton("Удалить") { _, _ ->
                    startActivity(Intent(requireActivity(), SplashActivity::class.java))
                    val database: FirebaseDatabase =
                        Firebase.database(ExtraUtil.FIREBASE_DATABASE_ADDRESS)
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
            with(MaterialAlertDialogBuilder(requireContext())) {
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
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(ExtraUtil.LINK_PRIVACY_POLICY)
                )
            )
            true
        }

        findPreference<Preference>("pref_lib")?.setOnPreferenceClickListener {
            startActivity(Intent(requireActivity(), LibActivity::class.java))
            true
        }

    }
}