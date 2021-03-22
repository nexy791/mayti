package com.ribsky.mayti.presentation.presenter.rating

import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.presentation.view.rating.RatingFragmentContract
import com.ribsky.mayti.ui.activity.main.MainActivity

class RatingFragmentPresenter(private val mView: RatingFragmentContract.View) :
    RatingFragmentContract.Presenter {

    private var users: ArrayList<UserModel> = ArrayList()
    private var isAll: Boolean = false

    override fun onCreate() {
        loadNextDataFromApi(0, 10)
    }

    override fun onLoadMore(offset: Int, offset1: Int) {
        if (!isAll) loadNextDataFromApi(offset, offset1)
    }

    override fun onItemClick(position: Int) {
        mView.openProfile(users[position])
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

}