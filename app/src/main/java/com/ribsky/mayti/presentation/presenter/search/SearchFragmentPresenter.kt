package com.ribsky.mayti.presentation.presenter.search

import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.presentation.view.search.SearchFragmentContract

class SearchFragmentPresenter(private val mView: SearchFragmentContract.View) :
    SearchFragmentContract.Presenter {

    private var users: ArrayList<UserModel> = ArrayList()
    private var isAll: Boolean = false
    private var usersInList: ArrayList<UserModel> = ArrayList()

    override fun onCreate(users: ArrayList<UserModel>) {
        this.users.addAll(users)
        this.users.shuffle()
        loadNextDataFromApi(0, 10)
    }

    override fun onCardSwiped(pos: Int) {
        if (!isAll) {
            loadNextDataFromApi(
                pos,
                pos + 10
            )
        } else {
            mView.emptyVisible()
        }
    }

    private fun loadNextDataFromApi(offset: Int, offset1: Int) {
        for (i in offset..offset1) {
            if (i < users.size) {
                usersInList.add(users[i])
                mView.updateCardStackView(i, users[i])
            } else {
                isAll = true
            }
        }
    }
}