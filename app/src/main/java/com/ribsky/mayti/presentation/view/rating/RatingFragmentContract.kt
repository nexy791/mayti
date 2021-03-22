package com.ribsky.mayti.presentation.view.rating

import com.ribsky.mayti.model.user.UserModel


class RatingFragmentContract {

    interface View {
        fun openProfile(userModel: UserModel)
    }

    interface Presenter {
        fun onCreate()
        fun onLoadMore(offset: Int, offset1: Int)
        fun onItemClick(position: Int)
    }

}