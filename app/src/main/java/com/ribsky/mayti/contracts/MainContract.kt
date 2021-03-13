package com.ribsky.mayti.contracts

import com.google.firebase.auth.FirebaseUser
import com.ribsky.mayti.model.UserModel

class MainContract {

    interface View {
        fun criticalError()
        fun startApp(user: UserModel, users: java.util.ArrayList<UserModel>)
        fun criticalErrorAuth()
        fun userBlocked()
    }

    interface Presenter {
        fun onCreate()
    }

    interface Repository {
        fun getAuthUser(): FirebaseUser?
        fun initialization(callback: (result: Boolean) -> Unit)
        fun getUsers(): ArrayList<UserModel>
    }

}