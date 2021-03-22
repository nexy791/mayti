package com.ribsky.mayti.ui.fragment.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialFadeThrough
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.FragmentIntro2Binding
import com.ribsky.mayti.model.game.GameModel
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.ui.activity.intro.IntroActivity
import com.ribsky.mayti.ui.adapters.game.RecyclerViewAdapterGames
import com.ribsky.mayti.ui.adapters.utils.RecyclerItemClickListener
import com.ribsky.mayti.util.ExtraUtil


class Intro2Fragment : Fragment() {

    private var _binding: FragmentIntro2Binding? = null
    private val binding get() = _binding!!

    private lateinit var games: ArrayList<GameModel>

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

        _binding = FragmentIntro2Binding.inflate(inflater, container, false)

        initRecyclerView()


        binding.buttonNext.setOnClickListener {

            val checkIds: ArrayList<Int> = ArrayList()
            for ((index, value) in games.withIndex()) {
                if (value.checked) {
                    checkIds.add(index)
                }
            }
            with((requireActivity() as IntroActivity)) {
                if (checkIds.isNotEmpty()) {
                    currentAccount.games = checkIds
                    writeUser(currentAccount)
                }
                goFragment(2)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}