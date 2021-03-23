package com.ribsky.mayti.ui.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialFadeThrough
import com.ribsky.mayti.databinding.FragmentSearchBinding
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.presentation.presenter.search.SearchFragmentPresenter
import com.ribsky.mayti.presentation.view.search.SearchFragmentContract
import com.ribsky.mayti.ui.activity.main.MainActivity
import com.ribsky.mayti.ui.adapters.search.CardStackAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction


class SearchFragment : Fragment(), CardStackListener, SearchFragmentContract.View {

    private var usersInList: ArrayList<UserModel> = ArrayList()
    private lateinit var mPresenter: SearchFragmentContract.Presenter

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    private lateinit var activity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        activity = requireActivity() as MainActivity
        mPresenter = SearchFragmentPresenter(this)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.cardStackView.layoutManager = CardStackLayoutManager(requireContext(), this)
        binding.cardStackView.adapter = CardStackAdapter(usersInList, this)

        mPresenter.onCreate(activity.getUsers())



        binding.btnRepeat.setOnClickListener {
            (requireActivity() as MainActivity).goFragment(0)
        }

        return binding.root
    }

    override fun updateCardStackView(i: Int, users: UserModel) {
        usersInList.add(users)
        binding.cardStackView.adapter!!.notifyItemInserted(i)
    }


    fun swipeClose() {
        binding.cardStackView.swipe()
    }

    override fun emptyVisible() {
        TransitionManager.beginDelayedTransition(binding.root, MaterialFadeThrough())
        binding.cardStackView.visibility = View.GONE
        binding.emptyState.visibility = View.VISIBLE
    }


    override fun onCardSwiped(direction: Direction?) {
        if (binding.cardStackView.adapter!!.itemCount == (binding.cardStackView.layoutManager as CardStackLayoutManager).topPosition) {
            val pos: Int =
                (binding.cardStackView.layoutManager as CardStackLayoutManager).topPosition
            mPresenter.onCardSwiped(pos)
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }
}