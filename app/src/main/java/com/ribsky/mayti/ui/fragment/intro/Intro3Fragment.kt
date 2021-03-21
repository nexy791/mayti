package com.ribsky.mayti.ui.fragment.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.transition.MaterialFadeThrough
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ribsky.mayti.databinding.FragmentIntro3Binding
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.ui.activity.intro.IntroActivity
import com.ribsky.mayti.util.ExtraUtil

class Intro3Fragment : Fragment() {

    private var _binding: FragmentIntro3Binding? = null
    private val binding get() = _binding!!

    private lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentIntro3Binding.inflate(inflater, container, false)


        binding.button.setOnClickListener {

            if (binding.textInputLayout.editText!!.text.toString().length <= 150) {

                with((requireActivity() as IntroActivity)) {
                    if (binding.textInputLayout.editText!!.text.toString().isNotEmpty()) {
                        currentAccount.bio = binding.textInputLayout.editText!!.text.toString()
                    }
                    goFragment(3)
                }
            }
        }
        return binding.root
    }


    fun writeUser(model: UserModel) {
        val database: FirebaseDatabase =
            Firebase.database(ExtraUtil.FIREBASE_DATABASE_ADDRESS)
        database.reference.root.child(model.uid).setValue(model.toMap())
        (requireActivity() as IntroActivity).currentAccount = model
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}