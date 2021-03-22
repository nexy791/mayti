package com.ribsky.mayti.presentation.view.main

import com.google.firebase.auth.FirebaseUser
import com.ribsky.mayti.model.user.UserModel

class MainActivityContract {

    interface View {
        fun criticalError()
        fun criticalErrorAuth()
        fun userBlocked()
        fun startApp(users: ArrayList<UserModel>)
    }

    interface Presenter {
        fun onCreate()
        fun getCurrentUser(): UserModel
        fun setCurrentUser(userModel: UserModel)
        fun getUsers(): ArrayList<UserModel>
    }

    interface Repository {
        fun getAuthUser(): FirebaseUser?
        fun initialization(callback: (result: Boolean) -> Unit)
        fun getUsers(): ArrayList<UserModel>
        fun getCurrentUser(): UserModel
        fun setCurrentUser(userModel: UserModel)
    }

}