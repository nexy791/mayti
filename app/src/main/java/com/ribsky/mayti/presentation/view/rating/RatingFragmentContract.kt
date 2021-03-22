package com.ribsky.mayti.presentation.view.rating

import com.ribsky.mayti.model.user.UserModel


class RatingFragmentContract {

    interface View {
        fun openProfile(userModel: UserModel)
        fun onNotifyDataSetChanged(users: ArrayList<UserModel>)
    }

    interface Presenter {
        fun onLoadMore(offset: Int, offset1: Int)
        fun onItemClick(position: Int)
        fun onCreate(usersAll: ArrayList<UserModel>)
    }

}