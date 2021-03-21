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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialFadeThrough
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.FragmentProfileBinding
import com.ribsky.mayti.ui.activity.main.MainActivity
import com.ribsky.mayti.util.AlertsUtil
import com.ribsky.mayti.util.ExtraUtil

class ProfileFragment : Fragment() {


    private var _binding: FragmentProfileBinding? = null
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

        _binding = FragmentProfileBinding.inflate(inflater, container, false)



        with((requireActivity() as MainActivity)) {
            binding.materialButton.text =
                "Лайков: " + currentCoin
            binding.textView.text = currentAccount.fln
            binding.textView2.text = currentAccount.bio
            binding.imageView.load(currentAccount.photo) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_playstore)
                transformations(CircleCropTransformation())
            }

            for (i in currentAccount.games) {
                if (i >= 0) {
                    binding.chips.addView(Chip(requireContext()).apply {
                        text = ExtraUtil.LIST_OF_GAMES[i]
                    })
                }
            }

        }

        binding.materialButton.setOnClickListener {
            AlertsUtil(requireActivity()).alertWhatIsLikes(this)
        }

        binding.btnAdd.setOnClickListener {
            AlertsUtil(requireActivity()).alertWhatIsLikes(this)
        }

        return binding.root
    }


    fun getLikes() {
        val alertDialog = MaterialAlertDialogBuilder(requireContext())
        alertDialog.setTitle("Получить лайки")
        alertDialog.setCancelable(true)
        alertDialog.setMessage("После просмотра рекламы, ты моментально получишь 1 лайк")
        alertDialog.setPositiveButton("Получить") { _, _ ->


            if ((requireActivity() as MainActivity).mRewardedAd != null) {
                (requireActivity() as MainActivity).mRewardedAd?.show(requireActivity()) {
                    (requireActivity() as MainActivity).currentCoin++
                    ExtraUtil().setLikes(
                        requireContext(),
                        (requireActivity() as MainActivity).currentAccount.uid,
                        (requireActivity() as MainActivity).currentCoin
                    )
                    (requireActivity() as MainActivity).updateBadger()
                    binding.materialButton.text =
                        "Лайков: " + (requireActivity() as MainActivity).currentCoin
                }
            } else {
                Snackbar.make(binding.root, "Реклама еще не готова", Snackbar.LENGTH_SHORT).show()
            }

            (requireActivity() as MainActivity).initAd()


        }
        alertDialog.setNegativeButton("Отмена") { _, _ -> }
        alertDialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}