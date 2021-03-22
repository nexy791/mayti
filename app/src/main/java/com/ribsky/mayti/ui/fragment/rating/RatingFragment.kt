package com.ribsky.mayti.ui.fragment.rating

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialFadeThrough
import com.ribsky.mayti.databinding.FragmentRatingBinding
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.presentation.presenter.rating.RatingFragmentPresenter
import com.ribsky.mayti.presentation.view.rating.RatingFragmentContract
import com.ribsky.mayti.ui.activity.main.MainActivity
import com.ribsky.mayti.ui.activity.user.UserInfoActivity
import com.ribsky.mayti.ui.adapters.rating.RecyclerItemAdapterRating
import com.ribsky.mayti.ui.adapters.utils.EndlessRecyclerViewScrollListener
import com.ribsky.mayti.ui.adapters.utils.RecyclerItemClickListener
import com.ribsky.mayti.util.ExtraUtil


class RatingFragment : Fragment(), RatingFragmentContract.View {

    private lateinit var mPresenter: RatingFragmentContract.Presenter

    private var _binding: FragmentRatingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        mPresenter = RatingFragmentPresenter(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRatingBinding.inflate(inflater, container, false)

        initRecyclerView()
        mPresenter.onCreate()

        return binding.root
    }


    private fun initRecyclerView() {

        val linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = linearLayoutManager


        binding.recyclerView.adapter =
            RecyclerItemAdapterRating(ArrayList())


        binding.recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                binding.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {

                    }

                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                })
        )

        val scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                mPresenter.onLoadMore(page + 10, totalItemsCount)
            }
        }
        binding.recyclerView.addOnScrollListener(scrollListener)
    }


    override fun openProfile(userModel: UserModel) {
        startActivityForResult(
            Intent(requireActivity(), UserInfoActivity::class.java)
                .putExtra(
                    "user",
                    userModel.toParcelable()
                ),
            ExtraUtil.RESULT_INFO
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ExtraUtil.RESULT_INFO) {
            (requireActivity() as MainActivity).currentCoin = ExtraUtil().getLikes(
                requireContext(),
                (requireActivity() as MainActivity).getCurrentUser().uid
            )
            (requireActivity() as MainActivity).updateBadger()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}