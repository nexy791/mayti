package com.ribsky.mayti.ui.fragment.intro

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialFadeThrough
import com.ribsky.mayti.databinding.FragmentIntro4Binding
import com.ribsky.mayti.presentation.presenter.intro.Intro4FragmentPresenter
import com.ribsky.mayti.presentation.view.intro.Intro4Contract
import com.ribsky.mayti.ui.activity.intro.IntroActivity

class Intro4Fragment : Fragment(), Intro4Contract.View {

    private lateinit var mPresenter: Intro4Contract.Presenter

    private var _binding: FragmentIntro4Binding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        mPresenter = Intro4FragmentPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentIntro4Binding.inflate(inflater, container, false)


        binding.buttonNext.setOnClickListener {
            mPresenter.buttonNextClicked(binding.textInputLayoutSocial.editText!!.text.toString())
        }

        binding.textInputLayoutSocial.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textInputLayoutSocial.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        return binding.root
    }


    override fun writeUser(social: String) {
        with((requireActivity() as IntroActivity)) {
            val newCurrentAccount = getCurrentUser()
            newCurrentAccount.social = social
            setCurrentUser(newCurrentAccount)
        }
    }

    override fun goFragment() {
        (requireActivity() as IntroActivity).goFragment(4)
    }

    override fun showError(text: String) {
        binding.textInputLayoutSocial.error = text
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

