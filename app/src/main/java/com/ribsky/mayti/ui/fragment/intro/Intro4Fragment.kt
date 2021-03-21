package com.ribsky.mayti.ui.fragment.intro

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.transition.MaterialFadeThrough
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ribsky.mayti.databinding.FragmentIntro4Binding
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.ui.activity.intro.IntroActivity
import com.ribsky.mayti.util.ExtraUtil

class Intro4Fragment : Fragment() {

    private var _binding: FragmentIntro4Binding? = null
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

        _binding = FragmentIntro4Binding.inflate(inflater, container, false)


        binding.button.setOnClickListener {

            if (binding.textInputLayout.editText!!.text.toString().isNotEmpty()) {

                with((requireActivity() as IntroActivity)) {
                    currentAccount.social =
                        "https://vk.com/" + binding.textInputLayout.editText!!.text.toString()
                    writeUser(currentAccount)
                    goFragment(4)

                }
            } else {
                binding.textInputLayout.error = "Необходимо указать ВК"
            }
        }

        binding.textInputLayout.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textInputLayout.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


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

