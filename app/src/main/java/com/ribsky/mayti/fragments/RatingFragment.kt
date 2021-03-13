package com.ribsky.mayti.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialFadeThrough
import com.ribsky.mayti.activities.MainActivity
import com.ribsky.mayti.activities.UserInfoActivity
import com.ribsky.mayti.adapters.EndlessRecyclerViewScrollListener
import com.ribsky.mayti.adapters.RecyclerItemAdapterRating
import com.ribsky.mayti.adapters.RecyclerItemClickListener
import com.ribsky.mayti.databinding.FragmentRatingBinding
import com.ribsky.mayti.model.UserModel
import com.ribsky.mayti.utils.ExtraUtils


class RatingFragment : Fragment() {

    private var _binding: FragmentRatingBinding? = null
    private val binding get() = _binding!!

    private var users: ArrayList<UserModel> = ArrayList()

    private var isAll: Boolean = false

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

        _binding = FragmentRatingBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {

        val linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = linearLayoutManager


        binding.recyclerView.adapter =
            RecyclerItemAdapterRating(users)

        loadNextDataFromApi(0, 10)

        binding.recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                binding.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        startActivityForResult(
                            Intent(requireActivity(), UserInfoActivity::class.java)
                                .putExtra(
                                    "user",
                                    users[position].toParcelable()
                                ),
                            ExtraUtils.RESULT_INFO
                        )
                    }

                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                })
        )

        val scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (!isAll) loadNextDataFromApi(page + 10, totalItemsCount)
            }
        }
        binding.recyclerView.addOnScrollListener(scrollListener)
    }

    fun loadNextDataFromApi(offset: Int, offset1: Int) {
        for (i in offset..offset1) {
            if (i < (requireActivity() as MainActivity).users.size) {
                users.add((requireActivity() as MainActivity).users.asReversed()[i])
            } else {
                isAll = true
            }
        }
        binding.recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ExtraUtils.RESULT_INFO) {
            (requireActivity() as MainActivity).currentCoin = ExtraUtils().getLikes(
                requireContext(),
                (requireActivity() as MainActivity).currentAccount.uid
            )
            (requireActivity() as MainActivity).updateBadger()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}