package com.ribsky.mayti.ui.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialFadeThrough
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.FragmentProfileBinding
import com.ribsky.mayti.ui.activity.main.MainActivity
import com.ribsky.mayti.util.AlertsUtil
import com.ribsky.mayti.util.ExtraUtil

class ProfileFragment : Fragment() {


    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var activity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        activity = requireActivity() as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        updateProfile()

        binding.buttonLikes.setOnClickListener {
            AlertsUtil(requireActivity()).alertLikesInfo(this)
        }

        binding.buttonPlus.setOnClickListener {
            AlertsUtil(requireActivity()).alertLikesInfo(this)
        }

        return binding.root
    }

    private fun updateProfile() {
        with(activity) {
            binding.buttonLikes.text =
                "Лайков: " + currentCoin
            binding.textViewName.text = getCurrentUser().fln
            binding.textViewDesc.text = getCurrentUser().bio
            binding.imageViewAvatar.load(getCurrentUser().photo) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_playstore)
                transformations(CircleCropTransformation())
            }

            for (i in getCurrentUser().games) {
                if (i >= 0) {
                    binding.chips.addView(Chip(requireContext()).apply {
                        text = ExtraUtil.LIST_OF_GAMES[i]
                    })
                }
            }

        }
    }

    fun getLikes() {
        with(MaterialAlertDialogBuilder(requireContext())) {

            setTitle("Получить лайки")
            setCancelable(true)
            setMessage("После просмотра рекламы, ты моментально получишь 1 лайк")
            setPositiveButton("Получить") { _, _ ->

                if (activity.mRewardedAd != null) {
                    activity.mRewardedAd?.show(requireActivity()) {
                        activity.currentCoin++
                        ExtraUtil().setLikes(
                            requireContext(),
                            activity.getCurrentUser().uid,
                            activity.currentCoin
                        )
                        activity.updateBadger()
                        binding.buttonLikes.text =
                            "Лайков: " + activity.currentCoin
                    }
                } else {
                    // error
                }

            }
            activity.initAd()

            setNegativeButton("Отмена") { _, _ -> }
            show()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}