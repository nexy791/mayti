package com.ribsky.mayti.presentation.view.search

import com.ribsky.mayti.model.user.UserModel

class SearchFragmentContract {

    interface View {
        fun emptyVisible()
        fun updateCardStackView(i: Int, users: UserModel)
    }

    interface Presenter {
        fun onCreate(users: ArrayList<UserModel>)
        fun onCardSwiped(pos: Int)
    }

}