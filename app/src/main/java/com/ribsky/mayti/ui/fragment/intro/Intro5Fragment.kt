package com.ribsky.mayti.ui.fragment.intro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.transition.MaterialFadeThrough
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.FragmentIntro5Binding
import com.ribsky.mayti.ui.activity.intro.IntroActivity
import com.ribsky.mayti.ui.activity.main.MainActivity

class Intro5Fragment : Fragment() {

    private var _binding: FragmentIntro5Binding? = null
    private val binding get() = _binding!!


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
        _binding = FragmentIntro5Binding.inflate(inflater, container, false)
        with((requireActivity() as IntroActivity)) {
            binding.textView.text = currentAccount.fln
            binding.imageView.load(currentAccount.photo) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_playstore)
                transformations(CircleCropTransformation())
            }
        }

        binding.fab.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}