package com.ribsky.mayti.presentation.presenter.rating

import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.presentation.view.rating.RatingFragmentContract

class RatingFragmentPresenter(private val mView: RatingFragmentContract.View) :
    RatingFragmentContract.Presenter {

    private var users: ArrayList<UserModel> = ArrayList()
    private var isAll: Boolean = false
    private var usersAll: ArrayList<UserModel> = ArrayList()

    override fun onCreate(usersAll: ArrayList<UserModel>) {
        this.usersAll = usersAll
        loadNextDataFromApi(0, 10)
    }

    override fun onLoadMore(offset: Int, offset1: Int) {
        if (!isAll) loadNextDataFromApi(offset, offset1)
    }

    override fun onItemClick(position: Int) {
        mView.openProfile(users[position])
    }

    private fun loadNextDataFromApi(offset: Int, offset1: Int) {
        for (i in offset..offset1) {
            if (i < usersAll.size) {
                users.add(usersAll.asReversed()[i])
            } else {
                isAll = true
            }
        }
        mView.onNotifyDataSetChanged(users)
    }
}