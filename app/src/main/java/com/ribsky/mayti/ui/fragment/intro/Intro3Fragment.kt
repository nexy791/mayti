package com.ribsky.mayti.ui.fragment.intro

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialFadeThrough
import com.ribsky.mayti.databinding.FragmentIntro3Binding
import com.ribsky.mayti.presentation.presenter.intro.Intro3FragmentPresenter
import com.ribsky.mayti.presentation.view.intro.Intro3Contract
import com.ribsky.mayti.ui.activity.intro.IntroActivity

class Intro3Fragment : Fragment(), Intro3Contract.View {

    private lateinit var mPresenter: Intro3Contract.Presenter

    private var _binding: FragmentIntro3Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        mPresenter = Intro3FragmentPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentIntro3Binding.inflate(inflater, container, false)


        binding.buttonNext.setOnClickListener {

            mPresenter.buttonNextClicked(binding.textInputLayoutBio.editText!!.text.toString())

        }

        binding.textInputLayoutBio.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textInputLayoutBio.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        return binding.root
    }

    override fun writeUser(bio: String) {
        with((requireActivity() as IntroActivity)) {
            val newCurrentAccount = getCurrentUser()
            newCurrentAccount.bio = bio
            setCurrentUser(newCurrentAccount)
        }
    }

    override fun goFragment() {
        (requireActivity() as IntroActivity).goFragment(3)
    }

    override fun showError(text: String) {
        binding.textInputLayoutBio.error = text
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}