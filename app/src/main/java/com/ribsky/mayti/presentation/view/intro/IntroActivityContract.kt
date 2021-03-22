package com.ribsky.mayti.presentation.view.intro

import com.ribsky.mayti.model.user.UserModel

class IntroActivityContract {

    interface View

    interface Presenter {
        fun getCurrentUser(): UserModel
        fun setCurrentUser(userModel: UserModel)

    }

    interface Repository {
        fun getCurrentUser(): UserModel
        fun setCurrentUser(userModel: UserModel)

    }

}