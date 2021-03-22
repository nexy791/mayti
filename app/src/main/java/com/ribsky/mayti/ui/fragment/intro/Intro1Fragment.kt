package com.ribsky.mayti.ui.fragment.intro

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialFadeThrough
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.FragmentIntro1Binding
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.ui.activity.intro.IntroActivity
import com.ribsky.mayti.util.ExtraUtil


class Intro1Fragment : Fragment() {

    private var _binding: FragmentIntro1Binding? = null
    private val binding get() = _binding!!

    private lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentIntro1Binding.inflate(inflater, container, false)

        binding.signInButton.setOnClickListener {
            signIn()
        }

        binding.textViewPrivacy.text = HtmlCompat.fromHtml(
            "<u>Продолжая, я принимаю правила и политику конфиденциальности</u>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.textViewPrivacy.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(ExtraUtil.LINK_PRIVACY_POLICY)
                )
            )
        }

        return binding.root
    }


    fun signIn() {
        startActivityForResult(
            mGoogleSignInClient.signInIntent,
            ExtraUtil.REQUEST_CODE_REGISTER_FIREBASE
        )
        binding.circularProgressIndicator.show()
    }

    fun showError() {
        binding.circularProgressIndicator.hide()
        Snackbar.make(binding.root, "Произошла ошибка", Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val mAuth = Firebase.auth
        if (requestCode == ExtraUtil.REQUEST_CODE_REGISTER_FIREBASE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
                mAuth.signInWithCredential(credential)
                    .addOnCompleteListener { task1 ->
                        if (task1.isSuccessful) {
                            binding.circularProgressIndicator.hide()
                            (requireActivity() as IntroActivity).goFragment(1)
                            with(mAuth.currentUser) {
                                (requireActivity() as IntroActivity).setCurrentUser(
                                    UserModel(
                                        uid, displayName, "",
                                        photoUrl.toString(), listOf(-1), "", false
                                    )
                                )
                            }
                        } else {
                            showError()
                        }
                    }
            } catch (e: ApiException) {
                showError()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}