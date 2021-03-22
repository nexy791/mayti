package com.ribsky.mayti.ui.fragment.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialFadeThrough
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.FragmentIntro2Binding
import com.ribsky.mayti.model.game.GameModel
import com.ribsky.mayti.presentation.presenter.intro.Intro2FragmentPresenter
import com.ribsky.mayti.presentation.view.intro.Intro2Contract
import com.ribsky.mayti.ui.activity.intro.IntroActivity
import com.ribsky.mayti.ui.adapters.game.RecyclerViewAdapterGames
import com.ribsky.mayti.ui.adapters.utils.RecyclerItemClickListener
import com.ribsky.mayti.util.ExtraUtil


class Intro2Fragment : Fragment(), Intro2Contract.View {

    private lateinit var mPresenter: Intro2Contract.Presenter

    private var _binding: FragmentIntro2Binding? = null
    private val binding get() = _binding!!

    private lateinit var games: ArrayList<GameModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        mPresenter = Intro2FragmentPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentIntro2Binding.inflate(inflater, container, false)

        initRecyclerView()


        binding.buttonNext.setOnClickListener {
            val checkIds: ArrayList<Int> = ArrayList()
            for ((index, value) in games.withIndex()) {
                if (value.checked) {
                    checkIds.add(index)
                }
            }
            mPresenter.buttonNextClicked(checkIds)
        }


        return binding.root
    }

    private fun initRecyclerView() {

        initGames()

        val linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = RecyclerViewAdapterGames(games)

        binding.recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                binding.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        games[position].checked = !games[position].checked
                        view!!.findViewById<CheckBox>(R.id.checkbox).isChecked =
                            games[position].checked
                    }

                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                })
        )


        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && binding.buttonNext.isShown) {
                    binding.buttonNext.hide()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.buttonNext.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }


    private fun initGames() {
        val arrayList: ArrayList<GameModel> = ArrayList()
        for ((index, value) in ExtraUtil.LIST_OF_GAMES.withIndex()) {
            arrayList.add(GameModel(index, value, false))
        }
        games = arrayList
    }


    override fun writeUser(checkIds: ArrayList<Int>) {
        with((requireActivity() as IntroActivity)) {
            val newCurrentAccount = getCurrentUser()
            newCurrentAccount.games = checkIds
            setCurrentUser(newCurrentAccount)
        }
    }

    override fun goFragment() {
        (requireActivity() as IntroActivity).goFragment(2)
    }

    override fun showError(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).setAnchorView(binding.buttonNext)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}