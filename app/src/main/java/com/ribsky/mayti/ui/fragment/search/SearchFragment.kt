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
import com.ribsky.mayti.ui.activity.main.MainActivity
import com.ribsky.mayti.ui.adapters.search.CardStackAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class SearchFragment : Fragment(), CardStackListener {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var users: ArrayList<UserModel> = ArrayList()
    private var isAll: Boolean = false
    private var usersInList: ArrayList<UserModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        (requireActivity() as MainActivity).sendUsers()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onEvent(users: ArrayList<UserModel>) {
        this.users.clear()
        this.users.addAll(users)
        this.users.shuffle()
        loadNextDataFromApi(0, 10)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.cardStackView.layoutManager = CardStackLayoutManager(requireContext(), this)
        binding.cardStackView.adapter = CardStackAdapter(usersInList, this)


        binding.send.setOnClickListener {
            (requireActivity() as MainActivity).goFragment(0)
        }

        return binding.root
    }


    fun swipeClose() {
        binding.cardStackView.swipe()
    }

    fun loadNextDataFromApi(offset: Int, offset1: Int) {
        for (i in offset..offset1) {
            if (i < users.size) {
                usersInList.add(users[i])
                binding.cardStackView.adapter!!.notifyItemInserted(i)
            } else {
                isAll = true
            }
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        if (binding.cardStackView.adapter!!.itemCount == (binding.cardStackView.layoutManager as CardStackLayoutManager).topPosition) {

            if (!isAll) {
                val pos: Int =
                    (binding.cardStackView.layoutManager as CardStackLayoutManager).topPosition + 10
                loadNextDataFromApi(
                    (binding.cardStackView.layoutManager as CardStackLayoutManager).topPosition,
                    pos
                )
            } else {
                TransitionManager.beginDelayedTransition(binding.root, MaterialFadeThrough())
                binding.cardStackView.visibility = View.GONE
                binding.empty.visibility = View.VISIBLE
            }
        }

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