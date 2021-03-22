package com.ribsky.mayti.presentation.presenter.main

import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.presentation.view.main.MainContract
import com.ribsky.mayti.repository.main.MainRepository

class MainActivityPresenter(val mView: MainContract.View) : MainContract.Presenter {

    private val mRepository: MainContract.Repository = MainRepository()


    override fun onCreate() {
        var isOk = false
        mRepository.initialization {
            if (it) {

                val users: ArrayList<UserModel> = mRepository.getUsers()
                var user: UserModel? = null

                for (i in users) {
                    if (i.uid == mRepository.getAuthUser()!!.uid) {
                        user = i
                        isOk = true
                    }
                }

                if (isOk) {
                    if (user!!.social.isNotBlank()) {

                        if (!user.isBlocked) {
                            mView.startApp(user, users)
                        } else {
                            mView.userBlocked()
                        }

                    } else {
                        mView.criticalErrorAuth()
                    }
                } else {
                    mView.criticalErrorAuth()
                }
            } else mView.criticalError()
        }
    }

}